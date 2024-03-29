#
# Copyright (C) 2022 Vaticle
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

load("@vaticle_dependencies//builder/java:rules.bzl", "typedb_kt_test")
load("@vaticle_dependencies//tool/checkstyle:rules.bzl", "checkstyle_test")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//:__pkg__"])

skiko_runtime_platform = select({
    "@vaticle_bazel_distribution//platform:is_linux_arm64": ["@maven//:org_jetbrains_skiko_skiko_awt_runtime_linux_arm64"],
    "@vaticle_bazel_distribution//platform:is_linux_x86_64": ["@maven//:org_jetbrains_skiko_skiko_awt_runtime_linux_x64"],
    "@vaticle_bazel_distribution//platform:is_mac_arm64": ["@maven//:org_jetbrains_skiko_skiko_awt_runtime_macos_arm64"],
    "@vaticle_bazel_distribution//platform:is_mac_x86_64": ["@maven//:org_jetbrains_skiko_skiko_awt_runtime_macos_x64"],
    "@vaticle_bazel_distribution//platform:is_windows": ["@maven//:org_jetbrains_skiko_skiko_awt_runtime_windows_x64"],
    "//conditions:default": ["INVALID"],
})

compose_test_libraries = [
    "//test/integration/data:paths",
    "//test/integration/common:common",
    "//test/integration:test-integration",
    "@maven//:junit_junit",
    "@maven//:org_jetbrains_compose_ui_ui_desktop",
    "@maven//:org_jetbrains_compose_ui_ui_graphics_desktop",
    "@maven//:org_jetbrains_compose_ui_ui_test_desktop",
    "@maven//:org_jetbrains_compose_ui_ui_test_junit4_desktop",
    "@maven//:org_jetbrains_compose_ui_ui_text_desktop",
    "@maven//:org_jetbrains_compose_ui_ui_unit_desktop",
    "@maven//:org_jetbrains_kotlin_kotlin_test",
    "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
    "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    "@maven//:org_jetbrains_skiko_skiko_awt",
]

kt_jvm_library(
    name = "test-integration",
    srcs = ["IntegrationTest.kt"],
    plugins = ["@vaticle_dependencies//builder/compose:compiler_plugin"],
    runtime_deps = skiko_runtime_platform,
    deps = [
        "//:studio",
        "//service",
        "//framework/common",

        # External Maven Dependencies
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_compose_runtime_runtime_desktop",
        "@maven//:org_jetbrains_compose_ui_ui_test_desktop",
        "@maven//:org_jetbrains_compose_ui_ui_test_junit4_desktop",
        "@maven//:org_jetbrains_kotlin_kotlin_test",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

typedb_kt_test(
    name = "test-quickstart",
    srcs = ["QuickstartTest.kt"],
    data = ["//test/integration/data:sample-github-data-files"],
    server_artifacts = {
        "@vaticle_bazel_distribution//platform:is_linux_arm64": "@vaticle_typedb_artifact_linux-arm64//file",
        "@vaticle_bazel_distribution//platform:is_linux_x86_64": "@vaticle_typedb_artifact_linux-x86_64//file",
        "@vaticle_bazel_distribution//platform:is_mac_arm64": "@vaticle_typedb_artifact_mac-arm64//file",
        "@vaticle_bazel_distribution//platform:is_mac_x86_64": "@vaticle_typedb_artifact_mac-x86_64//file",
        "@vaticle_bazel_distribution//platform:is_windows_x86_64": "@vaticle_typedb_artifact_windows-x86_64//file",
    },
    test_class = "com.vaticle.typedb.studio.test.integration.QuickstartTest",
    runtime_deps = skiko_runtime_platform,
    deps = compose_test_libraries,
)

typedb_kt_test(
    name = "test-queryrunner",
    srcs = ["QueryRunnerTest.kt"],
    data = ["//test/integration/data:sample-github-data-files"],
    server_artifacts = {
        "@vaticle_bazel_distribution//platform:is_linux_arm64": "@vaticle_typedb_artifact_linux-arm64//file",
        "@vaticle_bazel_distribution//platform:is_linux_x86_64": "@vaticle_typedb_artifact_linux-x86_64//file",
        "@vaticle_bazel_distribution//platform:is_mac_arm64": "@vaticle_typedb_artifact_mac-arm64//file",
        "@vaticle_bazel_distribution//platform:is_mac_x86_64": "@vaticle_typedb_artifact_mac-x86_64//file",
        "@vaticle_bazel_distribution//platform:is_windows_x86_64": "@vaticle_typedb_artifact_windows-x86_64//file",
    },
    test_class = "com.vaticle.typedb.studio.test.integration.QueryRunnerTest",
    runtime_deps = skiko_runtime_platform,
    deps = compose_test_libraries + [
        "//framework/material",
        "//service",
        "//service/connection",
        "//service/project",
        "//service/page",
        "//service/common",
        "@vaticle_typedb_driver//java/api:api",
    ],
)

kt_jvm_test(
    name = "test-projectbrowser",
    srcs = ["ProjectBrowserTest.kt"],
    data = ["//test/integration/data:sample-file-structure-files"],
    test_class = "com.vaticle.typedb.studio.test.integration.ProjectBrowserTest",
    runtime_deps = skiko_runtime_platform,
    deps = compose_test_libraries + [
        "//framework/material",
        "//service",
        "//service/page",
        "//service/project",
    ],
)

typedb_kt_test(
    name = "test-texteditor",
    srcs = ["TextEditorTest.kt"],
    data = [
        "//test/integration/data:sample-file-structure-files",
        "//test/integration/data:sample-github-data-files",
    ],
    server_artifacts = {
        "@vaticle_bazel_distribution//platform:is_linux_arm64": "@vaticle_typedb_artifact_linux-arm64//file",
        "@vaticle_bazel_distribution//platform:is_linux_x86_64": "@vaticle_typedb_artifact_linux-x86_64//file",
        "@vaticle_bazel_distribution//platform:is_mac_arm64": "@vaticle_typedb_artifact_mac-arm64//file",
        "@vaticle_bazel_distribution//platform:is_mac_x86_64": "@vaticle_typedb_artifact_mac-x86_64//file",
        "@vaticle_bazel_distribution//platform:is_windows_x86_64": "@vaticle_typedb_artifact_windows-x86_64//file",
    },
    test_class = "com.vaticle.typedb.studio.test.integration.TextEditorTest",
    runtime_deps = skiko_runtime_platform,
    deps = compose_test_libraries + [
        "//framework/material",
        "//service",
        "//service/common",
        "//service/connection",
        "//service/project",
        "//service/page",
        "//service/schema",

        # External Vaticle Dependencies
        "@vaticle_typedb_driver//java/api:api",
        "@vaticle_typeql//common/java:common",

        # External Maven Dependencies
        "@maven//:org_jetbrains_compose_runtime_runtime_desktop",
    ],
)

typedb_kt_test(
    name = "test-typebrowser",
    srcs = ["TypeBrowserTest.kt"],
    data = [
        "//test/integration/data:sample-file-structure-files",
        "//test/integration/data:sample-github-data-files",
    ],
    server_artifacts = {
        "@vaticle_bazel_distribution//platform:is_linux_arm64": "@vaticle_typedb_artifact_linux-arm64//file",
        "@vaticle_bazel_distribution//platform:is_linux_x86_64": "@vaticle_typedb_artifact_linux-x86_64//file",
        "@vaticle_bazel_distribution//platform:is_mac_arm64": "@vaticle_typedb_artifact_mac-arm64//file",
        "@vaticle_bazel_distribution//platform:is_mac_x86_64": "@vaticle_typedb_artifact_mac-x86_64//file",
        "@vaticle_bazel_distribution//platform:is_windows_x86_64": "@vaticle_typedb_artifact_windows-x86_64//file",
    },
    test_class = "com.vaticle.typedb.studio.test.integration.TypeBrowserTest",
    runtime_deps = skiko_runtime_platform,
    deps = compose_test_libraries + [
        "//framework/material",
        "//service",
        "//service/common",
        "//service/connection",
        "//service/project",
        "//service/page",
        "//service/schema",
        "@vaticle_typedb_driver//java/api:api",
    ],
)

checkstyle_test(
    name = "checkstyle",
    include = glob(["*"]),
    license_type = "agpl-header",
)
