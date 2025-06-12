REPEATS=1 STARTUP_PHRASE='successfully deployed\|simpleapp done' bash ../measure-startup-time.sh \
  payara6 http://localhost:8080/simpleapp target/payara6/glassfish/bin/startserv domain1 

# Disable Hazelcast in Payara with `asadmin set-hazelcast-configuration --enabled=false --dynamic=true`