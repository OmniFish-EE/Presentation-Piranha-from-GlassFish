# A Jakarta Faces application with Piranha Cloud Web Profile

## Build

```
mvn clean package
```

## Run

```
java -jar target/dependencies/piranha-dist-*.jar --webapp-dir target/faces --context-path /
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Started Piranha' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/ java -jar target/dependencies/piranha-dist-*.jar --webapp-dir target/faces --context-path /
```

# Run with runnable JAR with Project Leyden

Needs Java 25.

### Build AOT cache

```
java -XX:AOTCacheOutput=target/piranha.aot -jar target/dependencies/piranha-dist-*.jar --webapp-dir target/faces --context-path /
```

Then stop with Ctrl+C

### Run with AOT cache

```
java -XX:AOTCache=target/piranha.aot -jar target/dependencies/piranha-dist-*.jar --webapp-dir target/faces --context-path /
```

### Run with timing

```
STARTUP_PHRASE='Started Piranha' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/piranha java -XX:AOTCache=target/piranha.aot -jar target/dependencies/piranha-dist-*.jar --webapp-dir target/faces --context-path /
```

