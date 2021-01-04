FROM amazoncorretto:8-alpine-jdk

VOLUME ["/tmp", "/tmp/logs"]

RUN apk add --update ttf-dejavu ttf-ubuntu-font-family ca-certificates && rm -rf /var/cache/apk/*
ADD /target/*.jar app.jar

EXPOSE 8812

CMD java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker /app.jar