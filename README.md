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
> target/elasticbeanstalk-deploy.zip

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

##Customising
If you clone this project as a starting point, there are several places where configuration changes should be made.

### Elastic Beanstalk
There are several places that Elastic Beanstalk configuration exist.

> .elasticbeanstalk/config.yml

This file configures the environment and application names in AWS but more importantly specifies the path to the target/elastic-beanstalk.zip  file for deployment.

> src/main/elasticbeanstalk/Dockerrun.aws.json

This file allows further customisation of AWS - potentially, change the logging file name to be more meaningful.

> src/main/elasticbeanstalk/beanstalk.xml

This file controls the content of the deployment zip and shouldn't need changing.

### Docker
Docker configurations are in two places:

> src/main/fabric8/assembly.xml

This file controls the contents of the Docker image (in terms of build artifacts) and should not need to be changed unless you change the deployment method from fat jar or rename the fat jar artifact produced.

> pom.xml

The most important configurations are in the POM in the fabric8 plugin configuration. It's not essential to change anything other than potentially the URI used to test the image after running.

### Fat Jar
If you rename the entry point Verticle (likely) you will need to modify the Main-Verticle XML element to point to the new class.





