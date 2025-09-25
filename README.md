# Mini Observability POC

Spring Boot application wired with an in-memory H2 database and ready-to-run observability stack (Prometheus, Grafana, Tempo, and OpenTelemetry Collector).

## Prerequisites

- JDK 21 (wrapper config pulls it automatically if using Gradle toolchains)
- Docker (for the optional Prometheus/Grafana/Tempo stack)

## Running the Application

```bash
./gradlew bootRun
```

The service starts on `http://localhost:8080`.

## Useful Endpoints

- REST API docs: `http://localhost:8080/swagger-ui/index.html`
- Actuator health: `http://localhost:8080/actuator/health`
- Prometheus metrics: `http://localhost:8080/actuator/prometheus`
- H2 console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:miniobs`
  - User: `sa`, Password: *(blank)*

## Observability Stack

To bring up Prometheus, Grafana, Tempo, and the OTel Collector:

```bash
docker compose up -d
```

Dashboards and data sources are pre-configured via the files under `grafana/` and `prometheus.yml`.

## Development Tips

- Swagger UI is powered by SpringDoc (`springdoc-openapi` dependency).
- The H2 datasource can be overridden with environment variables `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` if you want to point at another database.
- Use `./gradlew test` to run the unit test suite.
