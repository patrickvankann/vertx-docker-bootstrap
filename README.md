#Vert.x Docker Bootstrap
A starter project for building Vert.x apps using the [Fabric8 Maven Docker Plugin](http://fabric8.io/guide/mavenPlugin.html) and Amazon Elastic Beanstalk. The Vert.x application is packaged as a "fat jar" and deployed within a Docker image derived from the standard java8 image.

##Structure
The Fabric8 Docker Maven Plugin configurations are stored in 
> src/main/fabric8

The assembly.xml file describes which build artifacts and resources are needed in the Docker image.

The Docker image is built in
> target/docker/${artifactId}

The Elastic Beanstalk configuration is in 
> src/main/elasticbeanstalk

This contains the configuration for Elastic Beanstalk (Dockerrun.aws.json) as well as another Maven Assembly Plugin file for building the zip file that can be deployed. This is prepared during the package phase in 
> target/${artifactId}-${version}-elasticbeanstalk.zip

A folder containing the uncompressed contents of the zip file is also created in the target directory to enable easy verification that the zip file has the correct contents. It should contain:
1. The Dockerfile created by the Fabric8 plugin
2. The Dockerrun.aws.json from src/elasticbeanstalk
3. Any resources needed by the Docker image referenced in the Dockerfile

The basic "Hello, world" Vert.x application is in 
> src/main/java/net/eusashead/vertx/docker/HttpVerticle.java

An integration test using Vertx Unit is in 
> src/test/java/net/eusashead/vertx/docker/HttpVerticleTest.java

##Building
The construction of Docker images and Elastic Beanstalk deployment zip files is bound to the Maven "package" goal. So executing
> mvn clean package

Should result in the creation of the Docker image, deployment in a Docker host, execution of integration tests and removal from the Docker host. If that succeeds the Maven Assembly plugin builds the zip archive for deployment to Amazon Elastic Beanstalk.

## Running
To run the Docker image locally in a Docker host, use:
> mvn docker:start

## Deploying
TODO use a plugin to push the Elastic Beanstalk zip file to Amazon.


