# Use uma imagem base do OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Copia pom.xml e o código
COPY pom.xml .
COPY src ./src

# Compila o projeto
RUN ./mvnw clean package -DskipTests

# Expõe a porta que o Spring Boot usa
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "target/image-resizer-api-1.0.0.jar"]
