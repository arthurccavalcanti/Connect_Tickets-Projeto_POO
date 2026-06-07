# JDK 21 como base
FROM maven:3.9.6-eclipse-temurin-21 AS meu-build
WORKDIR /app
COPY pom.xml .
# Baixa dependências do pom.xml (--quiet só mostra erros)
RUN mvn dependency:go-offline -q
COPY src ./src
# cria jar (pula testes)
RUN mvn package -DskipTests -q

# para produção, usa JRE como base
FROM eclipse-temurin:21-jre
WORKDIR /app
# copia .jar da etapa anterior
COPY --from=meu-build /app/target/meu-jar.jar app.jar
EXPOSE 8080
# executa jar
ENTRYPOINT ["java", "-jar", "app.jar"]
