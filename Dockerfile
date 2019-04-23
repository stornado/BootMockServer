FROM openjdk:8-jdk-alpine
# com.zxytech.mock.bootmockserver


VOLUME /tmp
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
