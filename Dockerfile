FROM openjdk:8-jdk-alpine
EXPOSE 8085
ADD target/LoanApplicationSystem-0.0.1-SNAPSHOT.jar credit-application-system.jar

ENTRYPOINT ["java","-jar","credit-application-system.jar"]