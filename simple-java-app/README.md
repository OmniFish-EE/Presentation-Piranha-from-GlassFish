# A simple Java application

## Build

```
mvn clean package
```

## Run

```
java -cp target/classes ee.omnifish.piranhafromgf.simple.SimpleJavaApp
```


## Run with timing

```
STARTUP_PHRASE='Hello from plain Java app' bash ../measure-startup-time.sh \
  plain-java - java -cp target/classes ee.omnifish.piranhafromgf.simple.SimpleJavaApp
```