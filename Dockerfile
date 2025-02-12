# Estágio 1: Build da aplicação (usando JDK)
FROM maven:3.8.6-openjdk-21-slim AS build

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo POM e o código-fonte
COPY pom.xml .
COPY src ./src

# Compilar o projeto e gerar o JAR
RUN mvn clean package -DskipTests

# Estágio 2: Execução da aplicação (usando JRE)
FROM openjdk:21-jre-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado no estágio de build
COPY --from=build /app/target/restaurantech-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]