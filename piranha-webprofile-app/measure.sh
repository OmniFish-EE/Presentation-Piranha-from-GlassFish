STARTUP_PHRASE='Started Piranha' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/ java -jar target/dependencies/piranha-dist-webprofile.jar --webapp-dir target/faces --context-path /