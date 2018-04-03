@echo off
echo [INFO] 使用maven根据pom.xml 复制依赖jar到src/main/webapp/WEB-INF/lib

cd ..
call mvn dependency:copy-dependencies -DincludeScope=runtime -DoutputDirectory=lib
cd target
rem copy misd*.jar %JBOSS_HOME%\server\default\deploy\
cd ..
cd bin
pause