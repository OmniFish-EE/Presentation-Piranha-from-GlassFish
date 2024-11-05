# Demo applications for the presentation Piranha Cloud - from Eclipse GlassFish to super fast Cloud and Serverless Java

The slides for the presentation are published here: [Piranha Cloud - from Eclipse GlassFish to super fast Cloud and Serverless Java](https://speakerdeck.com/omnifish/piranha-cloud-from-eclipse-glassfish-to-super-fast-cloud-and-serverless-java)

This repository contains

* One project with a very simple Hello world plain Java application: [simple-java-app](simple-java-app)
* Several projects with an application that serves a request using a simple Hello world Jakarta EE 9 Servlet but runs in different servlet containers:
  * [glassfish-4-app](glassfish-4-app) - runs on GlassFish 4 (which provides Java EE 7, so uses the Java EE 7 Servlet API)
  * [glassfish-7-app](glassfish-7-app) - runs on [Eclipse GlassFish](https://glassfish.org/) 7
  * [embedded-glassfish-app](embedded-glassfish-app) - runs on Eclipse GlassFish 7 Embedded
  * [springboot-app](springboot-app) - runs on [SpringBoot](https://spring.io/projects/spring-boot)
  * [tomcat-app](tomcat-app) - runs on plain Apache Tomcat server
  * [piranha-app](piranha-app) - runs on [Piranha Cloud](https://piranha.cloud/)
  * [piranha-lambda](piranha-lambda) - runs on [Piranha Cloud](https://piranha.cloud/) in [AWS Lambda](https://aws.amazon.com/lambda/)

The `simple-java-app` application is the simplest Java applicatoin possible - it only prints a message to the standard output and terminates. It's used as a benchmark.

Other applications serve a simple request and respond to it with a simple response that contains a text message. The script [measure-startup-time.sh](measure-startup-time.sh) can be used to measure the startup time until the application serves the first request. For the [piranha-lambda](piranha-lambda) application, AWS Lambda tools provide the measurement instead.

Each project contains a README file with instructions how to build it, run it and measure the startup time.
