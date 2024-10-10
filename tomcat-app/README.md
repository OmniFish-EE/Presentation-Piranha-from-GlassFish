# A simple servlet application with Spring Boot

## Build

```
mvn clean package
```

## Run

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.tomcat.TomcatApp
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Application started...' bash ../measure-startup-time.sh \
  tomcat http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.tomcat.TomcatApp
```