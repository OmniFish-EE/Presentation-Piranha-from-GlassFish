# A simple servlet application on GlassFish 4

## Build

```
mvn -Pdownload-gf clean package
```

## Run

```
target/glassfish4/bin/asadmin start-domain -v domain1
```

Then access the app at http://localhost:8080/simpleapp

## Run with timing

```
STARTUP_PHRASE='successfully deployed\|simpleapp done' bash ../measure-startup-time.sh \
  glassfish4 http://localhost:8080/simpleapp target/glassfish4/bin/asadmin start-domain -v domain1 
```