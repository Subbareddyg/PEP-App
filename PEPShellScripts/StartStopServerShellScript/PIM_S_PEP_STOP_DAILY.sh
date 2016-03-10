#!/bin/sh
echo "begin stoping the server"

cd /opt/IBM/WebSphere/wp_profile4/bin

./stopServer.sh  WebSphere_Portal -username wasadmin -password wasadmin


#Find out if any process is still running 

myarr=($(ps -ef|awk '/wp_profile4/&&!/awk/{print $2}'))

for i in "${myarr[@]}"
do
  
   if ps -p $i > /dev/null
	then
  		 echo "$i is still running even after stopping"
  		 # Do something knowing the pid exists, i.e. the process with $PID is running
		 echo "killing the running process " $i


		 #kill -9 $i
	fi
done

THISHOST=$(hostname --long)

#Sending email after stoping 

java -jar /opt/IBM/WebSphere/script/email.jar 0 $THISHOST