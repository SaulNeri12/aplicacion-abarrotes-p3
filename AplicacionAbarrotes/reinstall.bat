
@echo off

setlocal
rem VARIABLES NECESARIAS PARA RESETEAR LA BASE DE DATOS
set MYSQL_PUERTO=9999
set MYSQL_HOST=localhost
set MYSQL_USUARIO=root		
set BASEDATOS_NOMBRE=abarrotes
set MYSQL_RUTA_EXE="C:\xampp\mysql\bin\mysql.exe"

rem RESETEA LA BASE DE DATOS
%MYSQL_RUTA_EXE% -u %MYSQL_USUARIO% -h %MYSQL_HOST% -D %BASEDATOS_NOMBRE% -P %MYSQL_PUERTO% < "src\sql\resetearBDAbarrotes.sql"
%MYSQL_RUTA_EXE% -u %MYSQL_USUARIO% -h %MYSQL_HOST% -D %BASEDATOS_NOMBRE% -P %MYSQL_PUERTO% < "src\sql\crearBDAbarrotes.sql"
%MYSQL_RUTA_EXE% -u %MYSQL_USUARIO% -h %MYSQL_HOST% -D %BASEDATOS_NOMBRE% -P %MYSQL_PUERTO% < "src\sql\crearDatosPruebaAbarrotes.sql"
