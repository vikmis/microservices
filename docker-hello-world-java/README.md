# Dockerfile Examples

## Docker commands
- docker build -t in28min/hello-world-docker:v1 .


## Dockerfile - 1 - Creating Docker Images

```
FROM openjdk:21-slim
COPY target/*.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app.jar"]

## Dockerfile - 2
# Step 1: Use Maven with OpenJDK 21 for building the application
FROM maven:3.9.9-amazoncorretto-21-debian-bookworm AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

# Step 2: Use a lightweight OpenJDK 21 image to run the application
FROM openjdk:21-slim
EXPOSE 5000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]

```

## Dockerfile - 3 - Caching

```
FROM maven:3.9.9-amazoncorretto-21-debian-bookworm AS build
WORKDIR /home/app

COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/com/in28minutes/rest/webservices/restfulwebservices/RestfulWebServicesApplication.java	/home/app/src/main/java/com/in28minutes/rest/webservices/restfulwebservices/RestfulWebServicesApplication.java

RUN mvn -f /home/app/pom.xml clean package

COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:21.0-slim
EXPOSE 5000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
```
