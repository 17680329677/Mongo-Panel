FROM docker.v2.aispeech.com/aispeech/java-nginx:0.2.117-42f7e6c
MAINTAINER hezhe.du@aispeech.com

RUN echo "Asia/Shanghai" > /etc/timezone
COPY dashboard-front/dist /var/www/html
ADD dashboard/build/libs/dashboard-0.0.1-SNAPSHOT.jar /admin-panel/app.jar
ADD start.sh /start.sh
ADD default /etc/nginx/sites-enabled/default

EXPOSE 80 8090
CMD "./start.sh"