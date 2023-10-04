FROM openjdk:17
LABEL Description="Players Service"
VOLUME /tmp
COPY target/players-service*.jar players-service.jar
ENTRYPOINT ["java","-jar","players-service.jar","-Dspring.config.location=application.yml"]

EXPOSE 9091