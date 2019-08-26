FROM gradle:5.6.0-jdk11 AS build  
COPY --chown=gradle:gradle . /home/gradle/app
WORKDIR /home/gradle/app
RUN gradle shadowJar

FROM gcr.io/distroless/java:11
COPY --from=build /home/gradle/app/build/libs/app.jar /usr/app/app.jar
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
