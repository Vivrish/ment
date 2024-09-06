FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/ApiGateway-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
