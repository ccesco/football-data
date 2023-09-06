FROM openjdk:17
LABEL Description="Team Service"
VOLUME /tmp
COPY target/team-service*.jar team-service.jar
ENTRYPOINT ["java","-jar","team-service.jar","-Dspring.config.location=application.yml"]