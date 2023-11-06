# A simple servlet application with Piranha Cloud

## Build

```
mvn clean package
```

## Run

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.PiranhaApp
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Application started' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.PiranhaApp
```

## Run faster, without HTTP and logging

### Run

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.PiranhaMinimalApp
```

Prints a response immediately.

### Run with timing

```
STARTUP_PHRASE='Application started' bash ../measure-startup-time.sh \
  piranha-minimal - java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.PiranhaMinimalApp
```

## Run with Class Data Sharing

### Prepare class data

```
java -cp 'target/piranha-app-1.0-SNAPSHOT.jar:target/dependencies/*' \
  -XX:ArchiveClassesAtExit=target/piranha.jsa ee.omnifish.piranhafromgf.piranha.PiranhaApp cds
```

### Run with prepared class data

```
STARTUP_PHRASE='Application started' bash ../measure-startup-time.sh \
  piranha - java -cp 'target/piranha-app-1.0-SNAPSHOT.jar:target/dependencies/*' -XX:SharedArchiveFile=target/piranha.jsa ee.omnifish.piranhafromgf.piranha.PiranhaApp
```