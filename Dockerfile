# --- ETAPA 1: COMPILACIÓN (BUILD) ---
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# 1. Copiamos solo el pom.xml para descargar dependencias (aprovecha la caché de Docker)
COPY pom.xml .
# Descarga las dependencias (esto tardará un poco la primera vez)
RUN mvn dependency:go-offline -B

# 2. Copiamos TODO el código fuente (incluido src/main/resources/static...)
COPY src ./src

# 3. Compilamos el JAR dentro de Docker
RUN mvn clean package -DskipTests

# --- ETAPA 2: EJECUCIÓN (RUNTIME) ---
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos el JAR generado en la Etapa 1
COPY --from=build /app/target/academia-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]