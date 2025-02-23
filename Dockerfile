FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/update-trade-data-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources /app/data
WORKDIR /app
ENTRYPOINT ["java","-jar","/app.jar"]
