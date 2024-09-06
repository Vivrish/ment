FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/AuthenticationService-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "/app/application.jar"]