# -----------------------------
# Etapa 1: Build da aplicação com Maven (Java 21)
# -----------------------------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências offline
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Ajusta permissões (previne problemas de acesso)
RUN chmod -R 755 /app

# Compila o projeto e gera o WAR com logs detalhados em caso de falha
RUN mvn clean package -DskipTests -X || cat /app/target/*.log

# Lista os arquivos gerados para verificação
RUN ls -l /app/target

# -----------------------------
# Etapa 2: Deploy no WildFly 36.0.1.Final
# -----------------------------
FROM eclipse-temurin:21-jdk
WORKDIR /opt

# Baixa e extrai WildFly 36.0.1.Final
RUN curl -LO https://github.com/wildfly/wildfly/releases/download/36.0.1.Final/wildfly-36.0.1.Final.tar.gz \
    && tar xvf wildfly-36.0.1.Final.tar.gz \
    && mv wildfly-36.0.1.Final wildfly \
    && rm wildfly-36.0.1.Final.tar.gz

# Copia o WAR gerado para a pasta de deploy do WildFly
COPY --from=build /app/target/*.war /opt/wildfly/standalone/deployments/ROOT.war

# Expõe a porta padrão do WildFly
EXPOSE 8080

# Comando padrão: inicia o WildFly
CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
