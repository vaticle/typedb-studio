/*
 * Copyright (C) 2021 Vaticle
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

package com.vaticle.typedb.studio.viewer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.vaticle.typedb.studio.controller.Controller
import com.vaticle.typedb.studio.controller.common.Property
import com.vaticle.typedb.studio.controller.common.Property.Server.TYPEDB
import com.vaticle.typedb.studio.controller.common.Property.Server.TYPEDB_CLUSTER
import com.vaticle.typedb.studio.controller.connection.Connection.Status.CONNECTED
import com.vaticle.typedb.studio.controller.connection.Connection.Status.CONNECTING
import com.vaticle.typedb.studio.controller.connection.Connection.Status.DISCONNECTED
import com.vaticle.typedb.studio.viewer.common.Label
import com.vaticle.typedb.studio.viewer.common.component.Form
import com.vaticle.typedb.studio.viewer.common.theme.Theme

object Connection {

    private val WINDOW_WIDTH = 500.dp
    private val WINDOW_HEIGHT = 340.dp

    private object FormState {
        // We keep this static to maintain the values through application lifetime,
        // and easily accessible to all functions in this object without being passed around

        var server: Property.Server by mutableStateOf(TYPEDB)
        var address: String by mutableStateOf("")
        var username: String by mutableStateOf("")
        var password: String by mutableStateOf("")
        var tlsEnabled: Boolean by mutableStateOf(false)
        var caCertificate: String by mutableStateOf("")

        fun isValid(): Boolean {
            return when (server) {
                TYPEDB -> !address.isBlank()
                TYPEDB_CLUSTER -> !(address.isBlank() || username.isBlank() || password.isBlank())
            }
        }

        fun trySubmitIfValid() {
            if (isValid()) trySubmit()
        }

        fun trySubmit() {
            when (server) {
                TYPEDB -> Controller.connection.tryConnectToTypeDB(address)
                TYPEDB_CLUSTER -> when {
                    caCertificate.isBlank() -> Controller.connection.tryConnectToTypeDBCluster(
                        address, username, password, tlsEnabled
                    )
                    else -> Controller.connection.tryConnectToTypeDBCluster(address, username, password, caCertificate)
                }
            }
        }
    }

    @Composable
    fun Window() {
        Window(
            title = Label.CONNECT_TO_TYPEDB,
            onCloseRequest = { Controller.connection.showWindow = false },
            alwaysOnTop = true,
            state = rememberWindowState(
                placement = WindowPlacement.Floating,
                position = WindowPosition.Aligned(Alignment.Center),
                size = DpSize(WINDOW_WIDTH, WINDOW_HEIGHT)
            )
        ) {
            Form.Content(onSubmit = { FormState.trySubmitIfValid() }) {
                ServerFormField()
                AddressFormField()
                if (FormState.server == TYPEDB_CLUSTER) {
                    UsernameFormField()
                    PasswordFormField()
                    TLSEnabledFormField()
                    if (FormState.tlsEnabled) CACertificateFormField()
                }
                Spacer(Modifier.weight(1f))
                Row(verticalAlignment = Alignment.Bottom) {
                    ServerConnectionStatus()
                    Spacer(modifier = Modifier.weight(1f))
                    when (Controller.connection.status) {
                        DISCONNECTED -> DisconnectedFormButtons()
                        CONNECTED -> ConnectedFormButtons()
                        CONNECTING -> ConnectingFormButtons()
                    }
                }
            }
        }
    }

    @Composable
    private fun ServerFormField() {
        Form.Field(label = Label.SERVER) {
            Form.Dropdown(
                values = Property.Server.values().toList(),
                selected = FormState.server,
                onSelection = { FormState.server = it },
                enabled = Controller.connection.isDisconnected(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun AddressFormField() {
        Form.Field(label = Label.ADDRESS) {
            Form.TextInput(
                value = FormState.address,
                placeholder = Property.DEFAULT_SERVER_ADDRESS,
                onValueChange = { FormState.address = it },
                enabled = Controller.connection.isDisconnected(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun UsernameFormField() {
        Form.Field(label = Label.USERNAME) {
            Form.TextInput(
                value = FormState.username,
                placeholder = Label.USERNAME.lowercase(),
                onValueChange = { FormState.username = it },
                enabled = Controller.connection.isDisconnected(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun PasswordFormField() {
        Form.Field(label = Label.PASSWORD) {
            Form.TextInput(
                value = FormState.password,
                placeholder = Label.PASSWORD.lowercase(),
                onValueChange = { FormState.password = it },
                enabled = Controller.connection.isDisconnected(),
                isPassword = true,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

    @Composable
    private fun TLSEnabledFormField() {
        Form.Field(label = Label.ENABLE_TLS) {
            Form.Checkbox(
                value = FormState.tlsEnabled,
                onChange = { FormState.tlsEnabled = it },
                enabled = Controller.connection.isDisconnected(),
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun CACertificateFormField() {
        Form.Field(label = Label.CA_CERTIFICATE) {
            Form.TextInput(
                value = FormState.caCertificate,
                placeholder = "${Label.PATH_TO_CA_CERTIFICATE} (${Label.OPTIONAL.lowercase()})",
                onValueChange = { FormState.caCertificate = it },
                enabled = Controller.connection.isDisconnected(),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

    @Composable
    private fun ServerConnectionStatus() {
        val statusText = "${Label.STATUS}: ${Controller.connection.status.name.lowercase()}"
        Form.Text(
            value = statusText, color = when (Controller.connection.status) {
                DISCONNECTED -> Theme.colors.error2
                CONNECTING -> Theme.colors.quaternary
                CONNECTED -> Theme.colors.secondary
            }
        )
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun DisconnectedFormButtons() {
        Form.TextButton(text = Label.CANCEL, onClick = { Controller.connection.showWindow = false })
        Form.ComponentSpacer()
        Form.TextButton(text = Label.CONNECT, enabled = FormState.isValid(), onClick = { FormState.trySubmit() })
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun ConnectedFormButtons() {
        Form.TextButton(
            text = Label.DISCONNECT,
            onClick = { Controller.connection.disconnect() },
            textColor = Theme.colors.error2
        )
        Form.ComponentSpacer()
        Form.TextButton(text = Label.CLOSE, onClick = { Controller.connection.showWindow = false })
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun ConnectingFormButtons() {
        Form.TextButton(text = Label.CANCEL, onClick = { Controller.connection.disconnect() })
        Form.ComponentSpacer()
        Form.TextButton(text = Label.CONNECTING, onClick = {}, enabled = false)
    }
}