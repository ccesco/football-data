FROM openjdk:17
LABEL Description="Service Discovery"

COPY target/service-discovery*.jar service-discovery.jar
ENTRYPOINT ["java","-jar","service-discovery.jar","-Dspring.config.location=application.yml"]