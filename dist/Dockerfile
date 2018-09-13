FROM openjdk:8-jdk-alpine
ENV JAVA_OPTIONS -Xmx256m
COPY ./tech-task-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]