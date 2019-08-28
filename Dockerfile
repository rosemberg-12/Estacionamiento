FROM openjdk:8-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY ./build/libs/*.jar ./
EXPOSE 8080
CMD java -jar estacionamiento-backend-1.0.0.jar
