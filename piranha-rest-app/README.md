# A simple REST application with Piranha Cloud

## Build

```
mvn clean package
```

## Run

```
java -cp 'target/piranha-rest-app.jar:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.rest.PiranhaRestApp
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Application started' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/ java -cp 'target/piranha-rest-app.jar:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.rest.PiranhaRestApp
```

## Run with Class Data Sharing

### Prepare class data

```
java -cp 'target/piranha-rest-app.jar:target/dependencies/*' -XX:ArchiveClassesAtExit=target/piranha.jsa ee.omnifish.piranhafromgf.piranha.rest.PiranhaRestApp cds
```

### Run with prepared class data

```
STARTUP_PHRASE='Application started' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/ java -cp 'target/piranha-rest-app.jar:target/dependencies/*' -XX:SharedArchiveFile=target/piranha.jsa ee.omnifish.piranhafromgf.piranha.rest.PiranhaRestApp
```