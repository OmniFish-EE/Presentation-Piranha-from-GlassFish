# A simple REST application with Spring Boot

## Build

```
mvn clean package
```

## Run

```
java -jar target/springboot-rest-app-*.jar
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Started SpringBootRestApp' bash ../measure-startup-time.sh \
  spring-boot http://localhost:8080/ java -jar 'target/springboot-rest-app-*.jar'
```