@echo OFF

setlocal

cd %cd%
SET "CUR=%cd%"
IF EXIST "%CUR%\output" GOTO execute
@echo "execute apidoc"
apidoc -i docs -o "%CUR%\output"
goto end
:execute

IF NOT EXIST "%CUR\output%" GOTO NODIRECTORY
@echo "create output folder in current directory"
md "%CUR%\output"
goto end
:NODIRECTORY

pause;