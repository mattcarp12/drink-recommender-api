FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
COPY build/libs/drink-recommender-api-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]