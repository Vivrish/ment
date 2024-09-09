FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/EurekaServer-0.0.1-SNAPSHOT.jar /app/application.jar
RUN mkdir /logs
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app/application.jar"]