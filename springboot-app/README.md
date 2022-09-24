# A simple servlet application with Spring Boot

## Build

```
mvn clean package
```

## Run

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.springboot.SpringBootApp
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Started SpringBootApp' bash ../measure-startup-time.sh \
  spring-boot http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.springboot.SpringBootApp
```