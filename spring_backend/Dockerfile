FROM eclipse-temurin:18.0.2.1_1-jdk as build
WORKDIR ./Group-12-Final-Project

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw
RUN sed -i 's/\r$//' mvnw

RUN ./mvnw  install -DskipTests

ENTRYPOINT ["java","-jar","/root/.m2/repository/com/recipe/RecipeAPI/0.0.1-SNAPSHOT/RecipeAPI-0.0.1-SNAPSHOT.jar"]