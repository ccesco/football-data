FROM openjdk:17
LABEL Description="Init service"

COPY target/init-service*.jar init-service.jar
ENTRYPOINT ["java","-jar","init-service.jar","-Dspring.config.location=application.yml"]

EXPOSE 9094