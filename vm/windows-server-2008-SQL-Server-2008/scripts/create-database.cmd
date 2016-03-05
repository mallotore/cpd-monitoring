@echo off

echo Creating database...
"C:\Program Files\Microsoft SQL Server\100\Tools\Binn\SQLCMD.EXE" -S .\sqlexpress -i C:\vagrant\scripts\create-db.sql
echo Database is created