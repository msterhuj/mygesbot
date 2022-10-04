# Build bot
FROM gradle:7.5.1-jdk11 as builder

WORKDIR /app

COPY . .

RUN gradle clean jar

# Start bot
FROM openjdk:11-jre-slim

WORKDIR /app
LABEL maintainer="gabin.lanore@netbytes.space"
COPY --from=builder /app/build/libs/*.jar /app/app.jar

ENV TOKEN=change_me
ENV USERNAME=please
ENV PASSWORD=seriously

CMD ["java", "-jar", "app.jar"]