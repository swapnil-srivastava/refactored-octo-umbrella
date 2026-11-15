# refactored-octo-umbrella

Spring Boot 3.3.4 on Java 21, built with Maven, targeting Google Cloud Run + Cloud SQL. Owner: Swapnil
(`swapnil-srivastava`). The most developed of the Spring services: JPA on Cloud SQL Postgres, Kafka on Confluent Cloud (a `@KafkaListener` on `hello_world_topic`, a Kafka Streams processor on `shakespeare-topic`, topic config), WebClient/RestTemplate calls to other services, and `/hello`, `/hello-gcp`, `/hello-world`, `/message`, `/call-heroku-service`, `/call-guess-number-service`, plus the actuator.

## Commands

- Build and test: `./mvnw -B package` (what CI runs). Tests only: `./mvnw -B test`.
- Run locally: `./mvnw spring-boot:run` (port 8080). Health: `GET /actuator/health`.
- Docker: `./mvnw -B package && docker build -t swapnilsrivastava/gcp .` (the Dockerfile copies `target/*.jar`).
- The devcontainer has Java 21, Maven and Docker.

## Layout

- Code: `src/main/java/eu/swapnilsrivastava/swapnilsrivastava/`, base package
  `eu.swapnilsrivastava.swapnilsrivastava`. Tests mirror it under `src/test/java`.
- Config: `src/main/resources/application.properties`. Everything environment-specific is an env var set by the deploy workflow from GitHub secrets: `JDBC_DATABASE_URL`, `JDBC_DATABASE_USERNAME`, `JDBC_DATABASE_PASSWORD`, `KAFKA_BOOTSTRAP_SERVER`, `CLUSTER_API_KEY`, `CLUSTER_API_SECRET`. A new setting needs three edits: `application.properties`, the `--set-env-vars` line in `deploy-to-cloud-run.yml`, and a GitHub secret that Swapnil adds.

## CI and deploy

- `Java CI with Maven` runs on every push and pull request to `main`.
- When it succeeds on `main`, `Publish Docker image` pushes `swapnilsrivastava/gcp` to Docker Hub.
- **Merging to `main` deploys** (once CI is green). `Java CI with Maven` → `Publish Docker image` → `Deploy to Cloud Run` run in a chain, and the service is public (`--allow-unauthenticated`). `--no-cpu-throttling` is required so the Kafka listener keeps consuming; do not remove it.

## Things to know before changing anything

- The `Dockerfile` base `openjdk:21-slim` matches Java 21. The `openjdk` images are deprecated; `eclipse-temurin:21-jre` is the usual replacement if the base ever needs changing.
- `spring.jpa.hibernate.ddl-auto=update` is on: changing an `@Entity` changes the Cloud SQL schema on the next deploy. Treat entity changes as database changes and say so in the PR.
- **CI is red on every push (seen 2026-11-15).** CI runs the `@SpringBootTest` `contextLoads` test against the real Cloud SQL instance, and GCP answers "The Cloud SQL instance does not exist", so the app cannot start. Because the chain stops at CI, nothing has been deployed since; the live service may also be down. Fixing it needs Swapnil: recreate the instance, or point the `JDBC_DATABASE_URL` secret (used by CI) and `CLOUDSQL_INSTANCE` (used by the deploy) at one that exists. Separately, tests should not need a real database: add a test profile with H2 and `spring-kafka-test` (both already in the pom). Never delete or skip the test to get green.
- `spring.jpa.show-sql=true` and Kafka `DEBUG` logging are on and noisy/costly in Cloud Run logs.
- Secrets (database, Kafka, Docker Hub, cloud keys) live in GitHub secrets or the cloud
  console. Never commit or print them; tell Swapnil which ones to add.
- Don't deploy, create cloud resources or change cloud settings yourself. Open a PR
  and say what merging it will trigger.

## How this fits with the website

The main project is the website, `swapnil-srivastava/reimagined-guide`
(swapnilsrivastava.eu, Next.js + Supabase on Vercel). This repo is one of six small
backend services kept beside it as cloud/framework experiments. The website does not
depend on it for anything visitors use today. The agreed way to wire a backend in
later is a Next.js rewrite on the website with the backend URL in an env var
(`/svc/<name>/...`), so the browser never calls this service directly and CORS is
not needed.

For step-by-step work (new endpoint, config, shipping) use the `spring-service` skill
in `.claude/skills/`.
