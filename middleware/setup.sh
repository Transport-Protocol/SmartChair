#!/bin/sh

echo "check for java"
command -v java >/dev/null 2>&1 || { echo >&2 "I require java but it's not installed.  Aborting."; exit 1; }

echo "check for rabbitmq"
command -v rabbitmq >/dev/null 2>&1 || { echo >&2 "I require rabbitmq but it's not installed.  Aborting."; exit 1; }

echo "check for rabbitmqadmin"
command -v rabbitmqadmin >/dev/null 2>&1 || { echo >&2 "I require rabbitmqadmin but it's not installed.  Aborting."; exit 1; }

echo "check for git"
command -v git >/dev/null 2>&1 || { echo >&2 "I require git but it's not installed.  Aborting."; exit 1; }

echo "checkout git"
git clone https://github.com/Transport-Protocol/SmartChair.git

cd "SmartChair\middleware"

echo "run gradle build script"
if [[ -x "gradlew" ]]
./gradle buildMWRP

echo "everything seems in order, you may now change config values and start mwrp"