# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="sahajamit@gmail.com"

VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app/app.jar

WORKDIR app

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]