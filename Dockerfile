# ===== Stage 1: Build =====
FROM gradle:8-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
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

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
