FROM openjdk:8-jdk-alpine
EXPOSE 8085
ADD target/LoanApplicationSystem-0.0.1-SNAPSHOT.jar credit-application-system.jar

ENTRYPOINT ["java","-jar","credit-application-system.jar"]


## Dockerizing the app
#
# Create a Spring Boot Application
# Create Dockerfile
# Build executable jar file - mvn clean package
# Build Docker image - docker build -t credit-application-system:v1 .
# Run Docker container using the image built - docker run -p 8085:8085 credit-application-system:v1
# docker