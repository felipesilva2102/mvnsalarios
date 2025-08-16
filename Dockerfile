# Etapa 1: Build da aplicação com Maven (Java 21)
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN chmod -R 755 /app
RUN mvn clean package -DskipTests -X
RUN ls -l /app/target

# Etapa 2: Deploy no WildFly 36.0.1.Final
FROM eclipse-temurin:21-jdk
WORKDIR /opt
RUN curl -LO https://github.com/wildfly/wildfly/releases/download/36.0.1.Final/wildfly-36.0.1.Final.tar.gz \
    && tar xvf wildfly-36.0.1.Final.tar.gz \
    && mv wildfly-36.0.1.Final wildfly \
    && rm wildfly-36.0.1.Final.tar.gz

# Copia o WAR
COPY --from=build /app/target/*.war /opt/wildfly/standalone/deployments/ROOT.war

# Cria usuário administrativo
RUN /opt/wildfly/bin/add-user.sh admin Admin#123 --silent

EXPOSE 8080
CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
