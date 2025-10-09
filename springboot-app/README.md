# A simple servlet application with Spring Boot

## Build

```
mvn clean package
```

## Run

```
java -jar target/springboot-app-*.jar
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Started SpringBootApp' bash ../measure-startup-time.sh \
  spring-boot http://localhost:8080/ java -jar 'target/springboot-app-*.jar'
```

## Run with runnable JAR with Project Leyden

Needs Java 25.

### Build AOT cache

```
java -XX:AOTCacheOutput=target/springboot.aot -jar target/springboot-app-*.jar
```

Then stop with Ctrl+C

### Run with AOT cache

```
java -XX:AOTCache=target/springboot.aot -jar target/springboot-app-*.jar
```

### Run with timing

```
STARTUP_PHRASE='Started SpringBootApp' bash ../measure-startup-time.sh \
  spring-boot http://localhost:8080/ java -XX:AOTCache=target/springboot.aot -jar 'target/springboot-app-*.jar'
```

