# ===== Stage 1: Build =====
FROM gradle:8.14.2-jdk17-alpine AS builder

WORKDIR /app

# Gradle 캐시 최적화 (의존성만 먼저 다운로드)
COPY build.gradle settings.gradle gradle.properties ./
COPY gradle gradle
RUN gradle dependencies --no-daemon

# 소스 복사 및 빌드
COPY src ./src
RUN gradle clean build -x test --no-daemon

# fat JAR 추출 (plain.jar 제외)
RUN find build/libs -name "*.jar" ! -name "*plain.jar" -exec cp {} app.jar \;

# ===== Stage 2: Runtime =====
FROM gcr.io/distroless/java17-debian12

WORKDIR /app

# 보안: 비root 사용자 실행
USER nonroot:nonroot

# 빌드된 JAR 복사
COPY --from=builder --chown=nonroot:nonroot /app/app.jar ./

# 포트 노출
EXPOSE 8080

# 헬스체크 (Spring Boot actuator 기준)
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
