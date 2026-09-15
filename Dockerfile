# ========================================================
# ETAPA 1: Construcción del artefacto (Maven + Java 21)
# ========================================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /build

# Copiar configuración Maven y descargar dependencias (aprovechando caché de capas)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente y empaquetar el .jar ejecutable
COPY src ./src
RUN mvn clean package -DskipTests -B

# ========================================================
# ETAPA 2: Imagen de ejecución (solo JRE 21, liviana)
# ========================================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar el .jar ejecutable generado por Spring Boot (jar con servidor Tomcat embebido)
COPY --from=builder /build/target/*.jar app.jar

# Puerto en el que escucha la app; Render inyecta su propio $PORT en tiempo de ejecución
EXPOSE 8085

# server.port lee la variable de entorno PORT si existe (ver application.properties);
# así funciona igual en local (8085) y en plataformas como Render (puerto dinámico)
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
