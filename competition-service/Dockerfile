FROM openjdk:17
LABEL Description="Competition Service"
VOLUME /tmp
COPY target/competition-service*.jar competition-service.jar
ENTRYPOINT ["java","-jar","competition-service.jar","-Dspring.config.location=application.yml"]

EXPOSE 9099