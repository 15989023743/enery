#!/bin/sh
echo "[INFO] 使用maven编译项目，在Tomcat中卸载项目，然后以目录形式重新部署到Tomcat中."
echo "[INFO] 请确保Tomcat 6已启动."

cd ..
mvn tomcat:undeploy war:exploded tomcat:exploded
