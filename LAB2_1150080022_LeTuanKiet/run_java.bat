@echo off
chcp 65001 >nul
cd /d "%~dp0LAB2_MaHoaHienDai"
java -Dfile.encoding=UTF-8 -cp "target\classes;%USERPROFILE%\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\12.8.1.jre11\mssql-jdbc-12.8.1.jre11.jar;%USERPROFILE%\.m2\repository\org\bouncycastle\bcprov-jdk18on\1.78.1\bcprov-jdk18on-1.78.1.jar" week_02.frm_Menu
