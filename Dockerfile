# ============================================================
# Stage 1: Build
# ============================================================

FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom first for dependency caching
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copy source
COPY src ./src

# Build application
RUN mvn clean package -DskipTests


# ============================================================
# Stage 2: Runtime
# ============================================================

FROM eclipse-temurin:21-jre

WORKDIR /app

# Create non-root user
RUN useradd --system --create-home --shell /usr/sbin/nologin spring

COPY --from=builder /app/target/*.jar app.jar

RUN chown spring:spring app.jar

USER spring

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]