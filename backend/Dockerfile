# Etapa de build
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom e as fontes
COPY pom.xml .
COPY src ./src

# Build do projeto (ignora testes)
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o jar gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expõe porta
EXPOSE 8080

# Comando para rodar
ENTRYPOINT ["java","-jar","app.jar"]
