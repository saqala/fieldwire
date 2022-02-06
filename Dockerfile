### STAGE 1: Build ###
FROM node:16-slim AS build
RUN npm install -g @angular/cli
RUN apt update
RUN apt -y install default-jre
RUN apt -y install maven
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

### STAGE 2: Run ###

FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/imagems-0.0.1-SNAPSHOT.jar /usr/app/myapp.jar 
ENTRYPOINT ["java","-jar","/usr/app/myapp.jar"]


