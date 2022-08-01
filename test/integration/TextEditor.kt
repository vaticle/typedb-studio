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

// We need to access private function Studio.MainWindowColumn, this allows us to.
// Do not use this outside of tests anywhere. It is extremely dangerous to do so.
@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.vaticle.typedb.studio.test.integration

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.vaticle.typedb.client.TypeDB
import com.vaticle.typedb.client.api.TypeDBOptions
import com.vaticle.typedb.client.api.TypeDBSession
import com.vaticle.typedb.client.api.TypeDBTransaction
import com.vaticle.typedb.studio.test.integration.runComposeRule
import com.vaticle.typedb.studio.Studio
import com.vaticle.typedb.studio.framework.common.WindowContext
import com.vaticle.typedb.studio.framework.material.Icon
import com.vaticle.typedb.studio.state.StudioState
import com.vaticle.typedb.studio.state.project.FileState
import com.vaticle.typedb.studio.state.project.PathState
import com.vaticle.typeql.lang.TypeQL
import com.vaticle.typeql.lang.query.TypeQLMatch
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TextEditor {
    companion object {
        private const val DB_ADDRESS = "localhost:1729"
        private const val DB_NAME = "github"

        private val SAMPLE_DATA_PATH = File("test/data/sample_file_structure").absolutePath

        private val SAVE_ICON_STRING = Icon.Code.FLOPPY_DISK.unicode
        private val PLUS_ICON_STRING = Icon.Code.PLUS.unicode
    }

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `Make a New File and Save It`() {
        val funcName = object{}.javaClass.enclosingMethod.name
        runComposeRule(composeRule) {
            setContent {
                Studio.MainWindowContent(WindowContext(1000, 1000, 0, 0))
            }
            composeRule.waitForIdle()

            // We have to open a project to enable the '+' to create a new file.
            val path = cloneAndOpenProject(composeRule, SAMPLE_DATA_PATH, funcName)
            val x = StudioState.project.current!!.entries
            println(x)

            composeRule.onNodeWithText(PLUS_ICON_STRING).performClick()

            composeRule.waitForIdle()
            delay(500)

            composeRule.onNodeWithText("Untitled1.tql *").assertExists()

            // Clicking this takes us to a window to choose where to save - so we just assert that it exists and
            // can be clicked.
            composeRule.onNodeWithText(SAVE_ICON_STRING).assertExists().assertHasClickAction()

            composeRule.onNodeWithText(SAVE_ICON_STRING).performClick()
            val filePath = File("$path/Untitled1.tql").toPath()
            File("$path/Untitled1.tql").mkdirs()
            StudioState.project.saveFileDialog.file!!.trySave(filePath, true)
            StudioState.project.current!!.reloadEntries()

            composeRule.waitForIdle()
            delay(500)

            composeRule.onNodeWithText("Untitled1.tql").assertExists()
        }
    }

    @Ignore
    @Test
    fun `Schema Write and Commit`() {
        runComposeRule(composeRule) {
            setContent {
                Studio.MainWindowContent(WindowContext(1000, 1000, 0, 0))
            }
            composeRule.waitForIdle()
        }
    }

    @Ignore
    @Test
    fun `Data Write and Commit`() {
        runComposeRule(composeRule) {
            setContent {
                Studio.MainWindowContent(WindowContext(1000, 1000, 0, 0))
            }
            composeRule.waitForIdle()
        }
    }

    @Ignore
    @Test
    fun `Data Read Query`() {
        runComposeRule(composeRule) {
            setContent {
                Studio.MainWindowContent(WindowContext(1000, 1000, 0, 0))
            }
            composeRule.waitForIdle()
        }
    }

    @Ignore
    @Test
    fun `Data Read Requiring Infer Query`() {
        runComposeRule(composeRule) {
            setContent {
                Studio.MainWindowContent(WindowContext(1000, 1000, 0, 0))
            }
            composeRule.waitForIdle()
        }
    }
}