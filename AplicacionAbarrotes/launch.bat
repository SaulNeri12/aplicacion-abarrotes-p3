
@echo off

setlocal
rem VARIABLES NECESARIAS PARA CREAR LA BASE DE DATOS
set MYSQL_PUERTO=9999
set MYSQL_HOST=localhost
set MYSQL_USUARIO=root		
set BASEDATOS_NOMBRE=abarrotes
set MYSQL_RUTA_EXE="C:\xampp\mysql\bin\mysql.exe"
rem Archivo ejecutable JAR del programa
set "ABARROTES_JAR=dist/AplicacionAbarrotes.jar"
rem Ruta de JAVA 8
set "JAVA_RUTA_EXE=C:\Program Files\Java\jdk1.8.0_144\bin\java.exe"

rem Crea la base de datos abarrotes si no existe
%MYSQL_RUTA_EXE% -u %MYSQL_USUARIO% -h %MYSQL_HOST% -P %MYSQL_PUERTO% -e "CREATE DATABASE IF NOT EXISTS abarrotes"

%MYSQL_RUTA_EXE% -u %MYSQL_USUARIO% -h %MYSQL_HOST% -D %BASEDATOS_NOMBRE% -P %MYSQL_PUERTO% < "src\sql\crearBDAbarrotes.sql"
%MYSQL_RUTA_EXE% -u %MYSQL_USUARIO% -h %MYSQL_HOST% -D %BASEDATOS_NOMBRE% -P %MYSQL_PUERTO% < "src\sql\crearDatosPruebaAbarrotes.sql"


echo [*] Se creo la base de datos...

if exist "%ABARROTES_JAR%" (
	echo [!] Ejecutando archivo JAR de la aplicacion...
	"%JAVA_RUTA_EXE%" -jar "%ABARROTES_JAR%"
) else ( 
	rem Si no existe el archivo muestra mensaje
	echo [*] Ya puedes ejecutar el programa desde tu NetBeans 8
)

endlocal

pause

rem Muestra los datos (para prueba)
rem echo %ABARROTES_JAR%
rem echo %MYSQL_PUERTO%
rem echo %MYSQL_USUARIO%
rem echo %BASEDATOS_NOMBRE%
rem echo %MYSQL_RUTA_EXE%

