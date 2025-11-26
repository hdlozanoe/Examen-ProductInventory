FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia pom.xml y descarga dependencias
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copia el código del microservicio product
COPY src ./src
RUN mvn -q clean package -DskipTests

# Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
