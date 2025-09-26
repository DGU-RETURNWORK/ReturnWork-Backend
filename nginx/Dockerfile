FROM nginx:1.25-alpine

RUN apk add --no-cache tzdata curl  # 필요한 패키지 설치
ENV TZ=Asia/Seoul

RUN rm /etc/nginx/conf.d/default.conf  # 기본 설정 파일 삭제 (우리 설정 사용)

COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/

RUN echo "OK" > /usr/share/nginx/html/health  # 헬스체크용 파일 생성