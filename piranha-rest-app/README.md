# A simple REST application with Piranha Cloud

## Build

```
mvn clean package
```

## Run

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.rest.PiranhaRestApp
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Application started' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.rest.PiranhaRestApp
```
