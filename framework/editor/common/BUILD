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
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = [
    "//framework/editor:__pkg__",
    "//framework/editor/highlighter:__pkg__",
])

kt_jvm_library(
    name = "common",
    srcs = glob(["*.kt"]),
    plugins = ["@vaticle_dependencies//builder/compose:compiler_plugin"],
    deps = [
        # External Vaticle Dependencies
        "@vaticle_typeql//common/java:common",

        # External Maven Dependencies
        "@maven//:org_jetbrains_compose_ui_ui_text_desktop",
    ],
    tags = ["maven_coordinates=com.vaticle.typedb:typedb-studio-framework-editor-common:{pom_version}"],
)

checkstyle_test(
    name = "checkstyle",
    include = glob(["*"]),
    license_type = "agpl-header",
)
