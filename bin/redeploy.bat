@echo off
echo [INFO] 使用maven编译项目，在Tomcat中卸载项目，然后以目录形式重新部署到Tomcat中.
echo [INFO] 请确保已启动Tomcat 6并已在conf/tomcat-users.xml中设置admin用户.

cd ..
call mvn tomcat:undeploy war:exploded tomcat:exploded
cd bin
pause