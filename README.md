# Vert.x Docker Bootstrap
A starter project for building Vert.x apps using the [Fabric8 Maven Plugin](https://maven.fabric8.io/) and [Arquillian Cube](http://arquillian.org/arquillian-cube/) for integration testing. 

The Vert.x application is packaged as a "fat jar" using the Maven Shade plugin and the Fabric8 "zero-config" option for automatically building a Docker image and Kubernetes configuration.

## Structure
The basic "Hello, world" Vert.x application is in 
> src/main/java/net/eusashead/vertx/docker/HttpVerticle.java

An integration test using Vertx Unit is in 
> src/test/java/net/eusashead/vertx/docker/HttpVerticleTest.java

An integration test run using the Failsafe plugin and Arquillian is in 
> src/test/java/net/eusashead/vertx/docker/ArquillianTest.java





