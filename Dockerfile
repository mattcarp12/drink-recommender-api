FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
COPY build/libs/drink-recommender-api-0.0.1-SNAPSHOT.jar app.jar
#ENV JDBC_DATABASE_URL="jdbc:postgresql://localhost:5432/postgres"
#ENV JDBC_DATABASE_USERNAME="postgres"
#ENV JDBC_DATABASE_PASSWORD="postgres"
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]