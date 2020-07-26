#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY lib /home/app/lib
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/Transportflow-Backend.jar /usr/local/lib/app.jar
EXPOSE 4567
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]