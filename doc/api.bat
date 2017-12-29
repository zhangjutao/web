@echo off

cd %cd%
SET "CUR=%cd%"
IF EXIST "%CUR%\output" GOTO execute
IF NOT EXIST "%CUR\output%" GOTO NODIRECTORY

:execute
@echo "execute apidoc"
apidoc -i docs -o "%CUR%\output"

:NODIRECTORY
@echo "create output folder in current directory"
md "%CUR%\output"
apidoc -i docs -o "%CUR%\output"

pause;