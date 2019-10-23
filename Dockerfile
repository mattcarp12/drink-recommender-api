FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
COPY build/libs/*.jar app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]