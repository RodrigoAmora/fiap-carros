FROM openjdk:17-jdk-slim

WORKDIR /app

# Adicionar usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring


COPY target/*.jar app.jar

# Ajustar permissões
RUN chown spring:spring /app/app.jar

# Mudar para usuário não-root
USER spring


# Configurar healthcheck
HEALTHCHECK --interval=30s --timeout=3s \
    CMD wget -q --spider http://localhost:8081/actuator/health || exit 1


EXPOSE 8081

# Adicionar configurações da JVM para containers
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
