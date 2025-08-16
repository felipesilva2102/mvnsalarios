# Etapa 1: Build da aplicação com Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências (para cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Deploy no WildFly 36
FROM quay.io/wildfly/wildfly:36.0.1.Final
# Copia o WAR gerado para a pasta de deploy do WildFly
COPY --from=build /app/target/*.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

# Expõe a porta padrão do WildFly
EXPOSE 8080

# Comando padrão: inicia o WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
