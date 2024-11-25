
FROM gradle:8-jdk17 AS build
WORKDIR /app
COPY build.gradle /app/
COPY settings.gradle /app/
COPY src /app/src
RUN gradle clean build -x test

# Etapa de ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
