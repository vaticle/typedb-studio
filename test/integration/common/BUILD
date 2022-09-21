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

load("@vaticle_dependencies//tool/checkstyle:rules.bzl", "checkstyle_test")
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

package(default_visibility = ["//test/integration:__pkg__"])

skiko_runtime_platform = select({
     "@vaticle_dependencies//util/platform:is_mac": ["@maven//:org_jetbrains_skiko_skiko_awt_runtime_macos_x64"],
     "@vaticle_dependencies//util/platform:is_linux": ["@maven//:org_jetbrains_skiko_skiko_awt_runtime_linux_x64"],
     "@vaticle_dependencies//util/platform:is_windows": ["@maven//:org_jetbrains_skiko_skiko_awt_runtime_windows_x64"],
     "//conditions:default": ["INVALID"]})

kt_jvm_library(
    name = "common",
    srcs = glob(["*.kt"]),
    kotlin_compiler_plugin = "@org_jetbrains_compose_compiler//file",
    runtime_deps = skiko_runtime_platform,
    deps = [
        "//:studio",
        "//framework/material",
        "//framework/common",
        "//state",
        "//state/app",
        "//state/common",
        "//state/connection",
        "//state/project",
        "//state/page",

        # External Vaticle Dependencies
        "@vaticle_typedb_client_java//api",
        "@vaticle_typedb_client_java//:client-java",
        "@vaticle_typedb_common//:common",
        "@vaticle_typedb_common//test:typedb-runner",
        "@vaticle_typeql//java:typeql-lang",
        "@vaticle_typeql//java/common:common",
        "@vaticle_typeql//java/query:query",

        # External Maven Dependencies
        "@maven//:io_github_microutils_kotlin_logging_jvm",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_compose_runtime_runtime_desktop",
        "@maven//:org_jetbrains_compose_ui_ui_test_desktop",
        "@maven//:org_jetbrains_compose_ui_ui_test_junit4_desktop",
        "@maven//:org_jetbrains_kotlin_kotlin_test",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
        "@maven//:org_slf4j_slf4j_api",
    ],
)

checkstyle_test(
    name = "checkstyle",
    include = glob(["*"]),
    license_type = "agpl-header",
)