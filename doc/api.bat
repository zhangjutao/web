@echo OFF
@echo "check core-database-doc exists"
IF NOT EXIST D:\core-database-doc GOTO NODIRECTORY
:NODIRECTORY
@echo "CREATING core-database-doc"
md "d:\core-database-doc"
cd %cd%
SET cur=%cd%
@echo "execute apidoc"
apidoc -i docs -o D:/core-database-doc
pause;