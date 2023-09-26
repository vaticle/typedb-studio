/*
 * Copyright (C) 2022 Vaticle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.vaticle.typedb.studio.service.schema

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vaticle.typedb.driver.api.TypeDBTransaction
import com.vaticle.typedb.driver.api.concept.Concept.Transitivity.EXPLICIT
import com.vaticle.typedb.driver.api.concept.type.RelationType
import com.vaticle.typedb.driver.api.concept.type.RoleType
import com.vaticle.typedb.driver.api.concept.type.Type
import com.vaticle.typedb.studio.service.common.NotificationService.Companion.launchAndHandle
import com.vaticle.typedb.studio.service.common.util.Label
import com.vaticle.typedb.studio.service.common.util.Message.Companion.UNKNOWN
import com.vaticle.typedb.studio.service.common.util.Message.Schema.Companion.FAILED_TO_CHANGE_OVERRIDDEN_RELATED_ROLE_TYPE
import com.vaticle.typedb.studio.service.common.util.Message.Schema.Companion.FAILED_TO_CHANGE_OVERRIDDEN_RELATED_ROLE_TYPE_TO_REMOVE
import com.vaticle.typedb.studio.service.common.util.Message.Schema.Companion.FAILED_TO_DEFINE_RELATES_ROLE_TYPE
import com.vaticle.typedb.studio.service.common.util.Message.Schema.Companion.FAILED_TO_DELETE_TYPE
import com.vaticle.typedb.studio.service.common.util.Sentence
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.streams.toList
import mu.KotlinLogging

class RelationTypeState internal constructor(
    conceptType: RelationType,
    supertype: RelationTypeState?,
    schemaSrv: SchemaService
) : ThingTypeState<RelationType, RelationTypeState>(conceptType, supertype, Encoding.RELATION_TYPE, schemaSrv) {

    companion object {
        private val LOGGER = KotlinLogging.logger {}
    }

    override val parent: RelationTypeState? get() = supertype
    var relatedRoleTypeProperties: List<RoleTypeState.RelatedRoleTypeProperties> by mutableStateOf(emptyList())
    val relatedRoleTypes: List<RoleTypeState> get() = relatedRoleTypeProperties.map { it.roleType }
    val relatedRoleTypesExplicit: List<RoleTypeState>
        get() = relatedRoleTypeProperties.filter { !it.isInherited }.map { it.roleType }

    private val loadedRelatedRoleTypePropsAtomic = AtomicBoolean(false)

    override fun isSameEncoding(conceptType: Type) = conceptType.isRelationType
    override fun asSameEncoding(conceptType: Type) = conceptType.asRelationType()!!
    override fun fetchSameEncoding(tx: TypeDBTransaction, label: String) = tx.concepts().getRelationType(label)
    override fun typeStateOf(type: RelationType) = schemaSrv.typeStateOf(type)

    override fun updateConceptType(label: String) {
        super.updateConceptType(label)
        relatedRoleTypesExplicit.forEach { it.updateConceptType() }
    }

    override fun requestSubtypesExplicit() = schemaSrv.mayRunReadTx { tx ->
        conceptType.getSubtypes(tx, EXPLICIT).toList()
    }

    fun overridableRelatedRoleTypes() = supertype?.relatedRoleTypes
        ?.filter { it != schemaSrv.rootRoleType }
        ?.sortedBy { it.scopedName } ?: listOf()

    override fun loadInheritables() {
        super.loadInheritables()
        loadRelatedRoleTypes()
        relatedRoleTypes.forEach { it.loadPlayerTypes() }
    }

    override fun loadOtherConstraints() {
        super.loadOtherConstraints()
        loadRelatedRoleTypes()
        relatedRoleTypes.forEach { it.loadSupertypes() }
    }

    fun loadRelatedRoleTypesRecursivelyAsync() = coroutines.launchAndHandle(notifications, LOGGER) {
        loadRelatedRoleTypesRecursively()
    }

    private fun loadRelatedRoleTypesRecursively() {
        loadRelatedRoleTypes()
        subtypesExplicit.forEach { it.loadRelatedRoleTypesRecursively() }
    }

    private fun loadRelatedRoleTypes() {
        val loaded = mutableSetOf<RoleType>()
        val properties = mutableListOf<RoleTypeState.RelatedRoleTypeProperties>()

        fun load(tx: TypeDBTransaction, roleTypeConcept: RoleType, isInherited: Boolean) {
            loaded.add(roleTypeConcept)
            schemaSrv.typeStateOf(roleTypeConcept)?.let { roleType ->
                roleType.loadConstraints()
                val overriddenType = conceptType.getRelatesOverridden(tx, roleTypeConcept)
                    ?.let { schemaSrv.typeStateOf(it) }
                val extendedType = when {
                    isInherited -> roleType
                    else -> overriddenType
                }?.relationType
                properties.add(
                    RoleTypeState.RelatedRoleTypeProperties(roleType, overriddenType, extendedType, isInherited)
                )
            }
        }

        schemaSrv.mayRunReadTx { tx ->
            if (!loadedRelatedRoleTypePropsAtomic.get()) {
                loadedRelatedRoleTypePropsAtomic.set(true)
                conceptType.getRelates(tx, EXPLICIT).forEach { load(tx, it, false) }
                conceptType.getRelates(tx).filter { !loaded.contains(it) && !it.isRoot }.forEach { load(tx, it, true) }
                relatedRoleTypeProperties = properties
            }
        }
    }

    override fun resetLoadedConnectedTypes() {
        loadedRelatedRoleTypePropsAtomic.set(false)
        relatedRoleTypeProperties = emptyList()
        super.resetLoadedConnectedTypes()
    }

    override fun initiateCreateSubtype(onSuccess: () -> Unit) =
        schemaSrv.createRelationTypeDialog.open(this, onSuccess)

    override fun tryCreateSubtype(
        label: String, isAbstract: Boolean
    ) = tryCreateSubtype(label, schemaSrv.createRelationTypeDialog) { tx ->
        val type = tx.concepts().putRelationType(label)
        if (isAbstract || !isRoot) {
            if (isAbstract) type.setAbstract(tx)
            if (!isRoot) type.setSupertype(tx, conceptType)
        }
    }

    override fun initiateChangeSupertype() = schemaSrv.changeRelationSupertypeDialog.open(this) {
        schemaSrv.execOnTypesUpdated()
        loadConstraintsAsync()
    }

    override fun tryChangeSupertype(
        supertypeState: RelationTypeState
    ) = super.tryChangeSupertype(schemaSrv.changeRelationSupertypeDialog) {
        conceptType.setSupertype(it, supertypeState.conceptType)
    }

    fun tryDefineRelatesRoleType(
        roleType: String, overriddenType: RoleTypeState?, onSuccess: (() -> Unit)?
    ) = schemaSrv.mayRunWriteTxAsync { tx ->
        try {
            overriddenType?.let {
                conceptType.setRelates(tx, roleType, it.name)
            } ?: conceptType.setRelates(tx, roleType)
            loadRelatedRoleTypes()
            onSuccess?.let { it() }
        } catch (e: Exception) {
            notifications.userError(LOGGER, FAILED_TO_DEFINE_RELATES_ROLE_TYPE, name, roleType, e.message ?: UNKNOWN)
        }
    }

    fun initiateChangeOverriddenRelatedRoleType(
        props: RoleTypeState.RelatedRoleTypeProperties
    ) = schemaSrv.changeOverriddenRelatedRoleTypeDialog.open(this, props)

    fun tryChangeOverriddenRelatedRoleType(
        roleType: RoleTypeState, overriddenType: RoleTypeState?
    ) = schemaSrv.mayRunWriteTxAsync { tx ->
        try {
            conceptType.let { r ->
                overriddenType?.let { o -> r.setRelates(tx, roleType.name, o.conceptType) }
                    ?: r.setRelates(tx, roleType.name)
            }
            loadRelatedRoleTypes()
            schemaSrv.changeOverriddenRelatedRoleTypeDialog.close()
        } catch (e: Exception) {
            overriddenType?.let {
                notifications.userError(
                    LOGGER, FAILED_TO_CHANGE_OVERRIDDEN_RELATED_ROLE_TYPE,
                    name, roleType.name, overriddenType.name
                )
            } ?: notifications.userError(
                LOGGER,
                FAILED_TO_CHANGE_OVERRIDDEN_RELATED_ROLE_TYPE_TO_REMOVE,
                name,
                roleType.name
            )
        }
    }

    fun initiateDeleteRoleType(roleType: RoleTypeState) = schemaSrv.confirmation.submit(
        title = Label.CONFIRM_TYPE_DELETION,
        message = Sentence.CONFIRM_TYPE_DELETION.format(Encoding.ROLE_TYPE.label, roleType.scopedName),
        onConfirm = { tryDeleteRoleType(roleType) }
    )

    fun tryDeleteRoleType(roleType: RoleTypeState) = schemaSrv.mayRunWriteTxAsync {
        try {
            conceptType.unsetRelates(it, roleType.conceptType)
            loadConstraintsAsync()
        } catch (e: Exception) {
            notifications.userError(LOGGER, FAILED_TO_DELETE_TYPE, encoding.label, e.message ?: UNKNOWN)
        }
    }

    override fun purge() {
        super.purge()
        relatedRoleTypesExplicit.forEach { it.purge() }
    }

    override fun toString(): String = "TypeState.Relation: $conceptType"
}
