# ---- build stage ----
FROM --platform=$BUILDPLATFORM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Cache deps first
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

# Build
COPY src ./src
RUN mvn -B -DskipTests package \
 && JAR="$(ls -1 target/*.jar | grep -v 'original-' | head -n 1)" \
 && echo "Using jar: $JAR" \
 && cp "$JAR" /tmp/app.jar


# ---- run stage ----
FROM --platform=$BUILDPLATFORM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd -r -u 10001 appuser
COPY --from=build /tmp/app.jar /app/app.jar
RUN chown -R appuser:appuser /app
USER appuser

EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
