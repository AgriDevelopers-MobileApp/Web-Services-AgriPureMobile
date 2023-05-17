FROM maven:3.9.1-eclipse-temurin-17-focal AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY --from=build /home/app/target/AgriPureBackend-1.0.jar /usr/local/lib/app.jar
COPY start.sh start.sh
RUN chmod +x start.sh
ENTRYPOINT ["./start.sh"]