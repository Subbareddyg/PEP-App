#!/bin/sh

cd /opt/IBM/WebSphere/wp_profile4/bin


./startServer.sh  WebSphere_Portal 

OUT=$?
if [ $OUT -eq 0 ];then
   THISHOST=$(hostname --long)
   echo "sending email.."
   java -jar /opt/IBM/WebSphere/script/email.jar 1 $THISHOST

else
   echo "Cannot start the server , server may be already running ,Please trying stopping it before starting."
fi



