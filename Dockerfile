FROM amazoncorretto:17-alpine-jdk
MAINTAINER AGP
COPY target/AgriPureBackend-1.0.jar agp-app.jar
ENTRYPOINT ["java", "-jar", "/agp-app.jar"]
