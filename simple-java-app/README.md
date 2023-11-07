# A simple Java application

## Build

```
mvn clean package
```

## Plain Java app

### Run

```
java -cp target/classes ee.omnifish.piranhafromgf.simple.SimpleJavaApp
```


### Run with timing

```
STARTUP_PHRASE='Hello from plain Java app' bash ../measure-startup-time.sh \
  plain-java - java -cp target/classes ee.omnifish.piranhafromgf.simple.SimpleJavaApp
```

## Simple Java server

### Run

```
java -cp target/classes ee.omnifish.piranhafromgf.simple.SimpleHttpServer
```
### Run with timing

```
STARTUP_PHRASE='Simple server started' bash ../measure-startup-time.sh \
  javaserver http://localhost:8080 java -cp target/classes ee.omnifish.piranhafromgf.simple.SimpleHttpServer
```

