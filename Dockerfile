# Docker Build Stage
FROM maven:3-jdk-8-alpine AS build

#Build Stagge
WORKDIR /opt/app
COPY ./ /opt/app


RUN mvn clean install -DskipTests


#Docker Build Stage
FROM openjdk:8-jdk-alpine

COPY --from=build opt/app/target/*.jar app.jar

ENV PORT 8080

ENTRYPOINT ["java","-jar","-Dserver.port=${PORT}","app.jar"]
