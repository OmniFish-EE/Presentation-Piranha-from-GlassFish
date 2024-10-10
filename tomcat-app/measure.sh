STARTUP_PHRASE='Application started...' bash ../measure-startup-time.sh \
  tomcat http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.tomcat.TomcatApp