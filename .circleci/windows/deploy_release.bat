REM
REM Copyright (C) 2021 Vaticle
REM
REM This program is free software: you can redistribute it and/or modify
REM it under the terms of the GNU Affero General Public License as
REM published by the Free Software Foundation, either version 3 of the
REM License, or (at your option) any later version.
REM
REM This program is distributed in the hope that it will be useful,
REM but WITHOUT ANY WARRANTY; without even the implied warranty of
REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
REM GNU Affero General Public License for more details.
REM
REM You should have received a copy of the GNU Affero General Public License
REM along with this program.  If not, see <https://www.gnu.org/licenses/>.
REM

REM needs to be called such that software installed
REM by Chocolatey in prepare.bat is accessible
CALL refreshenv

ECHO Building Windows application image...

SET DEPLOY_ARTIFACT_USERNAME=%REPO_TYPEDB_USERNAME%
SET DEPLOY_ARTIFACT_PASSWORD=%REPO_TYPEDB_PASSWORD%

bazel --output_user_root=C:/b run //:deploy-windows-x86_64-exe --compilation_mode=opt -- release
IF %errorlevel% NEQ 0 EXIT /b %errorlevel%
