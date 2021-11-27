#!/bin/bash

REPOSITORY=/var/www/WTD-1.0.0
PROJECT_NAME=rc_wanted

cd $REPOSITORY/$PROJECT_NAME/

echo "[+] Git pull"
git pull &> /var/www/git_result.txt
sleep 2

RESULT=`cat /var/www/git_result.txt | grep fatal | wc -l`
if [ $RESULT -gt 0 ]; then
    echo "[-] git auth failed. Terminated"
    exit
fi

echo "[+] Build Project"
./gradlew build

echo "[+] Copy Build"
/bin/cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "[+] Check PID"
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "[+] Running PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "[-] No pid"
else
    echo "[+] kill $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "#==================================="
echo "\t RUN SERVER"
echo "#==================================="
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
echo "[+] JAR Name: $JAR_NAME"
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &

