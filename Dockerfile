# --- build stage: build the Spring Boot fat jar ---
FROM eclipse-temurin:21-jdk AS build
WORKDIR /src
COPY . .
RUN ./gradlew --no-daemon clean bootJar

# --- runtime stage: slim JRE + baked-in OTel agent ---
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Download the OpenTelemetry Java agent directly into the image (always correct file)
RUN mkdir -p /otel \
    && curl -L -o /otel/opentelemetry-javaagent.jar \
       https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.6.0/opentelemetry-javaagent.jar \
    && (file /otel/opentelemetry-javaagent.jar || true)

# Copy the fat jar produced in the build stage
COPY --from=build /src/build/libs/*-SNAPSHOT.jar /app/app.jar

# Attach the agent
ENV JAVA_TOOL_OPTIONS="-javaagent:/otel/opentelemetry-javaagent.jar -XX:+UseG1GC -XX:MaxRAMPercentage=75.0"

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
