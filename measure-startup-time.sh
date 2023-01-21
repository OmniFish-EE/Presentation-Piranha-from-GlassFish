#!/bin/bash

# measure time of the first request from a runtime (Piranha Micro, Quarkus)

# First argument is either quarkus, or piranha, or piranha-jlink, or piranha-hello
# Second is the URL where the app listens, or - if the app doesn't listen on a port
# The rest of the command line is the command to run the runtime, e.g. java -jar piranha-micro.jar --war-file app.war

if [ n$1 = n ]
  then
    echo Parameters: runtime-name app-url command-to-execute-to-start-app
    exit 1
fi

RUNTIME=$1
URL=$2
shift 2
COMMAND="$@"

ctrl_c(){
        kill `cat /tmp/$RUNTIME.pid` 
}

trap ctrl_c INT

case $RUNTIME in
  piranha)
    if [ x"$STARTUP_PHRASE" = x ]
      then
        STARTUP_PHRASE='It took'
      fi
    ;;
  piranha-jlink)
    STARTUP_PHRASE='Piranha started'
    ;;
  piranha-hello)
    STARTUP_PHRASE='Response: Hello'
    ;;
  quarkus)
    STARTUP_PHRASE='started in'
    ;;
esac

echo COMMAND: $COMMAND

rm -rf /tmp/$RUNTIME.pid
time ($COMMAND > /tmp/$RUNTIME.log 2>&1 & echo $! > /tmp/$RUNTIME.pid && tail -f /tmp/$RUNTIME.log | sed "/$STARTUP_PHRASE/ q" && test "$URL" "!=" - && echo && curl $URL 2>&1)
echo Press Enter to shutdown the application...
read
kill `cat /tmp/$RUNTIME.pid` 2>&1 | grep -v 'No such process'

