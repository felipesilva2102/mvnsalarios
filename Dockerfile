# Etapa 1: Build da aplicação com Maven (modo debug)
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências (para cache)
COPY pom.xml .
# Comando usado: mvn dependency:go-offline
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Ajusta permissões caso haja problemas
RUN chmod -R 755 /app

# Comando Maven para compilar e gerar o WAR (debug)
# Comando usado: mvn clean package -DskipTests
RUN mvn clean package -DskipTests -X

# Lista os arquivos gerados para verificação
RUN ls -l /app/target

# Etapa 2: Instala WildFly 36.0.1.Final
FROM eclipse-temurin:17-jdk
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
