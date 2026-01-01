# Etapa de build con Gradle y JDK 25
FROM eclipse-temurin:25-jdk-alpine AS build
LABEL authors="josias"

WORKDIR /app

# Copiamos Gradle wrapper y archivos de configuración primero (para aprovechar cache)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Copiamos el resto del código fuente
COPY src src

# Damos permisos al wrapper
RUN chmod +x gradlew

# Ejecutamos build con Gradle (sin tests)
RUN ./gradlew build -x test

# Etapa de runtime con JRE 25
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copiamos el jar generado desde la etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Variables de entorno para configuración
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
