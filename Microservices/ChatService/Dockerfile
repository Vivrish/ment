FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/ChatService-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8100
ENTRYPOINT ["java", "-jar", "/app/application.jar"]