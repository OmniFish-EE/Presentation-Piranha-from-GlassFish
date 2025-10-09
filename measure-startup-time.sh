#!/bin/bash

# measure time of the first request from a runtime (Piranha Micro, Quarkus)

# First argument is name of the runtime, used for creating temporary file names (for log output, for pid)
# The STARTUP_PHRASE env var should contain a grep selector matching the line in log that indicates server started 
# and app is deployed. For some runtimes, a default STARTUP_PHRASE is used if env var not set.
#
# Second is the URL where the app listens, or - if the app doesn't listen on a port
#
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
test -z "$REPEATS" && REPEATS=1
WAIT=no

kill_command_processes() {
    pid=`cat /tmp/$RUNTIME.pid`
    pgid=$pid

    kill -s TERM -"$pgid"
    while pgrep -g "$pgid" >/dev/null; do
      sleep 1
    done
}

ctrl_c(){
    kill_command_processes
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

for ((i=0; i<REPEATS; i++))
do
    echo COMMAND: $COMMAND

    rm -rf /tmp/$RUNTIME.pid
    rm -rf /tmp/$RUNTIME.log
    time (setsid $COMMAND > /tmp/$RUNTIME.log 2>&1 & echo $! > /tmp/$RUNTIME.pid && tail -f /tmp/$RUNTIME.log | sed "/$STARTUP_PHRASE/ q" > /dev/null && test "$URL" "!=" - && echo && until curl $URL; do sleep 0.01; done 2>&1)
    echo URL: $URL

    if [ "$WAIT" = "yes" ]
      then
        echo Press Enter to shutdown the application...
        read
    fi

    kill_command_processes
done
