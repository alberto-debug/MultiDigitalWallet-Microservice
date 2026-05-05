# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./
COPY .mvn .mvn/
COPY mvnw ./
COPY src src/

RUN chmod +x mvnw && ./mvnw -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8082
ENV SERVER_PORT=8082
ENTRYPOINT ["java", "-Dserver.port=${SERVER_PORT}", "-jar", "/app/app.jar"]
