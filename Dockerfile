FROM openjdk:8-jre-alpine

RUN apk add --no-cache bash

WORKDIR /opt

COPY server/target/links-0.0.1-SNAPSHOT.jar .

ENV PORT=8081

EXPOSE 8081

CMD ["java", "-Xms32m", "-Xmx128m", "-jar", "-Dserver.port=${PORT}", "links-0.0.1-SNAPSHOT.jar"]
