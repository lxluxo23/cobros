
FROM node:21-alpine AS frontend-build
WORKDIR /app
COPY cobros_front/package*.json ./
RUN npm install
COPY cobros_front/ ./
RUN npm run build

FROM gradle:8-jdk17 AS backend-build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src/ ./src
COPY --from=frontend-build /app/www ./src/main/resources/static
RUN gradle clean build -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=backend-build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]