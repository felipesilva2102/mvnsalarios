# Etapa 1: Build da aplicação com Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências (para cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Instala WildFly 36.0.1.Final
FROM eclipse-temurin:17-jdk
WORKDIR /opt

# Baixa e extrai WildFly 36.0.1.Final
RUN curl -LO https://github.com/wildfly/wildfly/releases/download/36.0.1.Final/wildfly-36.0.1.Final.tar.gz \
    && tar xvf wildfly-36.0.1.Final.tar.gz \
    && mv wildfly-36.0.1.Final wildfly \
    && rm wildfly-36.0.1.Final.tar.gz

# Copia o WAR da build para a pasta de deploy do WildFly
COPY --from=build /app/target/*.war /opt/wildfly/standalone/deployments/ROOT.war

# Expõe a porta padrão do WildFly
EXPOSE 8080

# Comando padrão: inicia o WildFly
CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
