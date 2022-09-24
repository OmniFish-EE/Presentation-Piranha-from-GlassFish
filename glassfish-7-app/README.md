# A simple servlet application on Eclipse GlassFish 7

## Build

```
mvn -Pdownload-gf clean package
```

## Run

```
target/glassfish7/bin/asadmin start-domain -v domain1
```

Then access the app at http://localhost:8080/simpleapp

## Run with timing

```
STARTUP_PHRASE='successfully deployed\|simpleapp done' bash ../measure-startup-time.sh \
  glassfish7 http://localhost:8080/simpleapp target/glassfish7/bin/asadmin start-domain -v domain1 
```