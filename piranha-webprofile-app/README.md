# A Jakarta Faces application with Piranha Cloud Web Profile

## Build

```
mvn clean package
```

## Run

```
java -jar target/dependencies/piranha-dist-webprofile.jar --webapp-dir target/faces --context-path /
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Started Piranha' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/ java -jar target/dependencies/piranha-dist-webprofile.jar --webapp-dir target/faces --context-path /
```