FROM eclipse-temurin:17-jdk-alpine as build
# Copy the maven, pom, and src files to the container.
COPY ./spring_backend/.mvn .mvn
COPY ./spring_backend/mvnw .
COPY ./spring_backend/pom.xml .
COPY ./spring_backend/src src
# Compile spring and dependencies
RUN ./mvnw -B package

# Copies the built jar file and runs it.
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/recipe-site-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "recipe-site-0.0.1-SNAPSHOT.jar"]
