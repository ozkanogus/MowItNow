# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace
COPY moveitnow/gradle moveitnow/gradle
COPY moveitnow/gradlew moveitnow/settings.gradle moveitnow/build.gradle moveitnow/
RUN --mount=type=cache,target=/root/.gradle ./moveitnow/gradlew -p moveitnow --no-daemon dependencies
COPY moveitnow/src moveitnow/src
RUN --mount=type=cache,target=/root/.gradle ./moveitnow/gradlew -p moveitnow --no-daemon clean bootJar

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S app && adduser -S app -G app
WORKDIR /app
COPY --from=build --chown=app:app /workspace/moveitnow/build/libs/*.jar app.jar
USER app
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=20s --retries=3 \
  CMD wget -q -O - http://localhost:8080/actuator/health >/dev/null || exit 1
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
