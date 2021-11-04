# syntax=docker/dockerfile:1
FROM gradle:7.2-jdk17 AS builder
WORKDIR /work

COPY ./build.gradle /work/build.gradle
#COPY ./gradlew /work/gradlew
COPY ./settings.gradle /work/settings.gradle
COPY ./gradle.properties /work/gradle.properties
#COPY ./gradlew.bat /work/gradlew.bat
COPY /src /work/src

RUN gradle build -x test  --scan --info --stacktrace

FROM openjdk:17-alpine
WORKDIR /app

EXPOSE 8010 8010

COPY --from=builder /work/build/libs/productstore-0.1-all.jar /app/bin/productstore.jar

RUN mkdir -p /app/db

ENTRYPOINT ["java","-XX:+UseSerialGC", "-Xss512k", "-Xmx128m", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/bin/productstore.jar"]