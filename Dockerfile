# Usar uma imagem base do JDK para rodar a aplicação
FROM openjdk:21-jdk-slim

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar os arquivos do projeto Maven para o contêiner (otimizar camadas de build)
COPY pom.xml ./
COPY src ./src

# Compilar o projeto dentro do contêiner (opcional, se preferir buildar localmente, mantenha como está)
RUN apt-get update && apt-get install -y maven \
    && mvn clean package -DskipTests \
    && rm -rf /var/lib/apt/lists/*

# Alternativa: se já está buildando o JAR localmente, mantenha apenas esta etapa:
COPY target/restaurantes-gerenciamento-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta usada pela aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
