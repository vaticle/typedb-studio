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

package com.vaticle.typedb.studio.service.common.util

object Label {
    const val ABSTRACT = "Abstract"
    const val ADDRESS = "Address"
    const val ADDRESSES = "Addresses"
    const val ADVANCED = "Advanced"
    const val APPLY = "Apply"
    const val AS = "As"
    const val ATTRIBUTE = "Attribute"
    const val ATTRIBUTES = "Attributes"
    const val ATTRIBUTE_TYPE = "Attribute Type"
    const val CANCEL = "Cancel"
    const val CASE_SENSITIVE = "Case Sensitive"
    const val CA_CERTIFICATE = "CA Certificate"
    const val CHANGE_ATTRIBUTE_SUPERTYPE = "Change Attribute Supertype"
    const val CHANGE_ENTITY_SUPERTYPE = "Change Entity Supertype"
    const val CHANGE_OVERRIDDEN_OWNED_ATTRIBUTE_TYPE = "Change Overridden Owned Attribute Type"
    const val CHANGE_OVERRIDDEN_RELATED_ROLE_TYPE = "Change Overridden Related Role Type"
    const val CHANGE_OVERRIDDEN_PLAYED_ROLE_TYPE = "Change Overridden Played Role Type"
    const val CHANGE_RELATION_SUPERTYPE = "Change Relation Supertype"
    const val CHANGE_SUPERTYPE = "Change Supertype"
    const val CHANGE_TYPE_ABSTRACTNESS = "Change Type Abstractness"
    const val CLOSE = "Close"
    const val CLOSE_TRANSACTION = "Close Transaction"
    const val CLOSING_TYPEDB_STUDIO = "Closing TypeDB Studio"
    const val CMD = "cmd"
    const val COLLAPSE = "Collapse"
    const val COMMIT = "Commit"
    const val COMMIT_TRANSACTION = "Commit Transaction"
    const val CONFIRM = "Confirm"
    const val CONFIRM_DIRECTORY_DELETION = "Confirm Directory Deletion"
    const val CONFIRM_FILE_DELETION = "Confirm File Deletion"
    const val CONFIRM_QUITTING_APPLICATION = "Confirm Quitting Application"
    const val CONFIRM_TYPE_DELETION = "Confirm Type Deletion"
    const val CONNECT = "Connect"
    const val CONNECTING = "Connecting..."
    const val CONNECT_TO_TYPEDB = "Connect to TypeDB"
    const val CONVERT_FILE_TYPE = "Converting File Type"
    const val COPY = "Copy"
    const val COPY_All = "Copy All"
    const val CREATE = "Create"
    const val CREATE_ADDRESS = "Create Address"
    const val CREATE_ATTRIBUTE_TYPE = "Create Attribute Type"
    const val CREATE_DATABASE = "Create Database"
    const val CREATE_DIRECTORY = "Create Directory"
    const val CREATE_ENTITY_TYPE = "Create Entity Type"
    const val CREATE_FILE = "Create file"
    const val CREATE_RELATION_TYPE = "Create Relation Type"
    const val CREATE_SUBTYPE = "Create Subtype"
    const val CTRL = "ctrl"
    const val CUT = "Cut"
    const val DATA = "Data"
    const val DATABASE = "Database"
    const val DATABASE_NAME = "database_name"
    const val DATA_SESSION = "Data Session"
    const val DECREASE_TEXT_SIZE = "Decrease Text Size"
    const val DEFAULT_SERVER_ADDRESS = "localhost:1729"
    const val DEFINE_OWNS_ATTRIBUTE_TYPE = "Define Owns Attribute Type"
    const val DEFINE_PLAYS_ROLE_TYPE = "Define Plays Role Type"
    const val DEFINE_RELATES_ROLE_TYPE = "Define Relates Role Type"
    const val DELETE = "Delete"
    const val DELETE_DATABASE = "Delete Database"
    const val DELETE_ROLE_TYPE = "Delete Role Type"
    const val DIRECTORY = "Directory"
    const val DIRECTORY_NAME = "Directory Name"
    const val DISCONNECT = "Disconnect"
    const val DISMISS_ALL = "Dismiss All"
    const val EDIT = "Edit"
    const val ENABLE_EDITOR_AUTOSAVE = "Enable Autosave"
    const val ENABLE_GRAPH_OUTPUT = "Enable Graph Output"
    const val ENABLE_INFERENCE = "Enable Inference"
    const val ENABLE_INFERENCE_EXPLANATION = "Enable Inference Explanation"
    const val ENABLE_SNAPSHOT = "Enable Snapshot"
    const val ENABLE_TLS = "Enable TLS"
    const val ENTER = "Enter"
    const val ENTITY = "Entity"
    const val EXACT_WORD = "Exact Word"
    const val EXPAND = "Expand"
    const val EXPAND_COLLAPSE = "Expand/Collapse"
    const val EXPLAIN = "Explain"
    const val EXPORT = "Export"
    const val EXPORT_SCHEMA = "Export Schema"
    const val EXPORT_SYNTAX = "Export Syntax"
    const val EXTENDED_TYPE = "Extended Type"
    const val E_G_ = "e.g."
    const val FILE_NAME = "File Name"
    const val FIND = "Find"
    const val FOUND = "Found"
    const val GO_TO_ATTRIBUTE_TYPE = "Go to Attribute Type"
    const val GO_TO_EXTENDED_TYPE = "Go to Extended Type"
    const val GO_TO_OVERRIDDEN_TYPE = "Go to Overridden Type"
    const val GO_TO_OWNER_TYPE = "Go to Owner Type"
    const val GO_TO_ROLE_TYPE = "Go to Role Type"
    const val GRAPH = "Graph"
    const val GRAPH_CONCEPT_PREVIEW_PLACEHOLDER = "Click on a Concept to inspect it"
    const val GRAPH_VISUALISER = "Graph Visualiser"
    const val INCREASE_TEXT_SIZE = "Increase Text Size"
    const val INFER = "Infer"
    const val INHERITED = "Inherited"
    const val INTERACTIVE_MODE = "Interactive Mode"
    const val INTERNAL_ID = "Internal ID"
    const val JUMP_AND_STICK_TO_BOTTOM = "Jump and Stick to Bottom"
    const val JUMP_TO_TOP = "Jump to Top"
    const val KEY = "Key"
    const val LABEL = "Label"
    const val LEARN_MORE = "Learn More"
    const val LOG = "Log"
    const val MANAGE_CLUSTER_ADDRESSES = "Manage Cluster Addresses"
    const val MANAGE_DATABASES = "Manage Databases"
    const val MANAGE_PREFERENCES = "Manage Preferences"
    const val MOVE = "Move"
    const val MOVE_DIRECTORY = "Move Directory"
    const val MS = "ms"
    const val NEXT_OCCURRENCE = "Next Occurrence"
    const val NO = "No"
    const val NONE = "None"
    const val NONE_IN_PARENTHESES = "($NONE)"
    const val NOT = "Not"
    const val OK = "OK"
    const val OPEN = "Open"
    const val OPEN_PREFERENCES = "Open Preferences"
    const val OPEN_PROJECT = "Open Project"
    const val OPEN_PROJECT_DIRECTORY = "Open Project Directory"
    const val OPTIONAL = "Optional"
    const val OPTIONAL_IN_PARENTHESES = "($OPTIONAL)"
    const val OUTPUT = "Output"
    const val OUTPUT_RESPONSE_TIME = "Output Response Time"
    const val OVERRIDDEN = "Overridden"
    const val OVERRIDDEN_TYPE = "Overridden Type"
    const val OWNERS = "Owners"
    const val OWNER_TYPE = "Owner Type"
    const val OWNS = "Owns"
    const val PASSWORD = "Password"
    const val PASTE = "Paste"
    const val PATH_OF_DIRECTORY = "/path/of/directory"
    const val PATH_TO_CA_CERTIFICATE = "/path/to/ca/certificate"
    const val PLAYS = "Plays"
    const val POPULATE_REQUIRED_FIELDS = "Populate Required Fields"
    const val PREFERENCE_INTEGER_WARNING = "Please input a positive integer."
    const val PREVIEW = "Preview"
    const val PREVIOUS_OCCURRENCE = "Previous Occurrence"
    const val PROJECT = "Project"
    const val PROJECT_BROWSER = "Project Browser"
    const val PROJECT_IGNORED_PATHS = "Set Ignored Paths"
    const val PROJECT_MANAGER = "Project Manager"
    const val PROPERTY = "Property"
    const val QUERY = "Query"
    const val QUERY_IS_RUNNING = "Query is Running"
    const val QUERY_RESPONSE_TIME = "Query Response Time"
    const val QUERY_RUNNER = "Query Runner"
    const val QUIT = "Quit"
    const val READ = "Read"
    const val READ_MORE = "Read More"
    const val READ_ONLY = "Read Only"
    const val READ_TRANSACTION = "Read Transaction"
    const val REFRESH = "Refresh"
    const val REGULAR_EXPRESSION = "Regular Expression"
    const val RELATES = "Relates"
    const val RELATION = "Relation"
    const val REMOVE = "Remove"
    const val RENAME = "Rename"
    const val RENAME_DIRECTORY = "Rename Directory"
    const val RENAME_FILE = "Rename File"
    const val RENAME_TYPE = "Rename Type"
    const val REOPEN = "Reopen"
    const val REPLACE = "Replace"
    const val REPLACE_ALL = "Replace All"
    const val REPLACE_ALL_OCCURRENCES = "Replace All Occurrences"
    const val REPLACE_NEXT_OCCURRENCE = "Replace Next Occurrence"
    const val RESET = "Reset"
    const val RESET_TEXT_SIZE = "Reset Text Size"
    const val ROLE = "Role"
    const val ROLES = "Roles"
    const val ROLE_TYPE = "Role Type"
    const val ROLLBACK = "Rollback"
    const val ROLLBACK_TRANSACTION = "Rollback Transaction"
    const val RULES = "Rules"
    const val RUN = "Run"
    const val RUN_FILE = "Run File"
    const val RUN_QUERY = "Run Query"
    const val RUN_SCRIPT = "Run Script"
    const val RUN_SELECTION = "Run Selection"
    const val SAVE = "Save"
    const val SAVE_CURRENT_FILE = "Save Current File"
    const val SAVE_FILE = "Save File"
    const val SAVE_OR_DELETE = "Save or Delete"
    const val SCHEMA = "Schema"
    const val SCHEMA_EXCEPTIONS = "Schema Exceptions"
    const val SCHEMA_SESSION = "Schema Session"
    const val SCRIPT_MODE = "Script Mode"
    const val SELECT_ATTRIBUTE_TYPE = "Select Attribute Type"
    const val SELECT_CERTIFICATE_FILE = "Select Certificate File"
    const val SELECT_DATABASE = "Select Database"
    const val SELECT_OVERRIDDEN_TYPE_OPTIONAL = "Select Overridden Type (Optional)"
    const val SELECT_PREFERENCE_GROUP = "Select a preference group from the menu on the left."
    const val SELECT_ROLE_TYPE = "Select Role Type"
    const val SERVER = "Server"
    const val SESSION_TYPE = "Session Type"
    const val SET_QUERY_LIMIT = "Default Match Query Limit"
    const val SHIFT = "Shift"
    const val SNAPSHOT = "Snapshot"
    const val STATUS = "Status"
    const val STOP_SIGNAL = "Stop Signal"
    const val SUBMIT = "Submit"
    const val SUBTYPES = "Subtypes"
    const val SUBTYPES_OF = "Subtypes of"
    const val SUPERTYPE = "Supertype"
    const val TABLE = "Table"
    const val TEXT_CURSOR_POSITION = "Text Cursor Position"
    const val TEXT_EDITOR = "Text Editor"
    const val THING = "Thing"
    const val TITLE = "Title"
    const val TRACE = "Trace"
    const val TRANSACTION_STATUS = "Transaction Status"
    const val TRANSACTION_TYPE = "Transaction Type"
    const val TYPE = "Type"
    const val TYPEDB_STUDIO = "TypeDB Studio"
    const val TYPEDB_STUDIO_APPLICATION_ERROR = "TypeDB Studio Application Error"
    const val TYPES = "Types"
    const val TYPE_BROWSER = "Type Browser"
    const val TYPE_LABEL = "Type Label"
    const val UNDEFINE_OWNED_ATTRIBUTE_TYPE = "Undefine Owned Attribute Type"
    const val UNDEFINE_PLAYED_ROLE_TYPE = "Undefine Played Role Type"
    const val UNTITLED = "Untitled"
    const val USERNAME = "Username"
    const val USERS = "Users"
    const val VALUE = "Value"
    const val VALUE_TYPE = "Value Type"
    const val WRITE = "Write"
    const val WRITE_TRANSACTION = "Write Transaction"
    const val YES = "Yes"
}
