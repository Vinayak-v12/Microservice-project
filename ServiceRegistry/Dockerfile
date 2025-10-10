# ---------- Stage 1: Build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy built jar from the previous stage
COPY --from=build /app/target/ServiceRegistry-0.0.1-SNAPSHOT.jar app.jar

# Render will assign a random port
EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]
