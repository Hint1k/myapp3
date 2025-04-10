FROM openjdk:21-jdk-slim-buster
ENV PORT 8080
EXPOSE 8080
ARG JAR_FILE=build/libs/transaction-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} transaction.jar
ENTRYPOINT ["java","-jar","/transaction.jar"]