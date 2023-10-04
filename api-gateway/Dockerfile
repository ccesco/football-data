FROM openjdk:17
LABEL Description="API Gateway"
VOLUME /tmp
COPY target/api-gateway*.jar api-gateway.jar
ENTRYPOINT ["java","-jar","api-gateway.jar","-Dspring.config.location=application.yml"]

EXPOSE 9000