FROM openjdk:8-jdk-alpine
COPY build/libs/drink-recommender-api-0.0.1-SNAPSHOT.jar app.jar
ENV JDBC_DATABASE_URL $JDBC_DATABASE_URL
ENV JDBC_DATABASE_USERNAME $JDBC_DATABASE_USERNAME
ENV JDBC_DATABASE_PASSWORD $JDBC_DATABASE_PASSWORD
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]