@echo off
echo [INFO] 使用maven根据pom.xml
cd ..
call mvn deploy
cd bin
pause