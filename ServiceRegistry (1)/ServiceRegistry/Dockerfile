FROM eclipse-temurin:21-jdk

WORKDIR /app

copy target/ServiceRegistry-0.0.1-SNAPSHOT.jar  app.jar

EXPOSE 8003

ENTRYPOINT ["java","-jar","app.jar"]
