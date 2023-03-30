FROM eclipse-temurin:20-jdk-alpine

ADD ./spring_backend/target/recipe-site-0.0.1-SNAPSHOT.jar recipe-site-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "recipe-site-0.0.1-SNAPSHOT.jar"]
