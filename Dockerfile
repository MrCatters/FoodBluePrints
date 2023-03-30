FROM eclipse-temurin:20-jdk-alpine

ADD target/spring-0.0.1-SNAPSHOT.jar spring-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spring-0.0.1-SNAPSHOT.jar"]
