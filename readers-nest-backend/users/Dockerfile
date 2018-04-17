# First run this inside the project
# mvnw package && java -jar target/readers-nest-backend-users-0.0.1-SNAPSHOT.jar

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD target/readers-nest-backend-users-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 9000