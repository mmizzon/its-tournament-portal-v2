# Build: il progetto Maven sta in backend/arenahub-backend
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY arenahub-backend/pom.xml .
RUN mvn -q -B dependency:go-offline
COPY arenahub-backend/src ./src
RUN mvn -q -B package -DskipTests

# Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
