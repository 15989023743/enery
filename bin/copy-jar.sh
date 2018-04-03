#!/bin/sh
echo "[INFO] 使用maven根据pom.xml 复制依赖jar到src/main/webapp/WEB-INF/lib"

cd ..
mvn dependency:copy-dependencies