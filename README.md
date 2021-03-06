# Credit Application System
It is a restful credit application system that receives credit application requests and returns the credit result to the customer according to the relevant criteria. It is written using the Spring Boot framework.
For this credit application system project, which was built using monolithic architecture, the sms sending simulation project was made separately for the microservice architecture.
The project has been tried to be done in accordance with Solid principles. At the same time, sms sending simulation was done as a separate project to show that it is also suitable for microservice architecture.

Almost all of the unit and integration tests run successfully. Also swagger has been added and exceptions are handled as much as possible. The project was made in accordance with the Rest conventions. Postgresql and JPA/Hibernate was used.

In addition, when creating a customer, the customer's credit score is generated randomly.
### Architecture Used In The Project
* While developing this project, I preferred the monolithic architecture because it would be easier and faster to manage and develop due to the small size of the project, and for easy testing and error tracking. 
* At the same time, monolithic architecture is preferred because it will have a single jar file for deployment.
* The project consists of layers such as model, repository, service, controller using layered architecture.
* At the same time, the simulation of sending the credit application result to the customer via sms is done using the microservice architecture.
* This shows that this project can work in accordance with the microservice architecture and is open to new developments.

## Frontend
Frontend side is developed using the react library.
* Here is the link to the Frontend project.
  [Credit-Application-Frontend](https://github.com/vildntn/Credit-Application-Frontend)
## Helper Project with RabbitMQ
After applying for a credit to the customer, the simulation of sending an sms was made with a microservice architecture.
Communication between the two architectures takes place with RabbitMQ.

* Here is the link to the Helper project.
  [Credit-Application-Helper](https://github.com/vildntn/Credit-Application-Helper)


### Requirements
For Building and running the application belows are required:

* Spring Boot 2.6.2
* JDK 8
* Maven 3.8.3
* PostgreSQL
* Swagger 3.0.0
* RabbitMQ (Requires Erlang)
* Lombok
* JUnit
* Docker

### Functional Requirements
* New customers can be created in the system, existing customers can be updated or deleted.
* If the credit score is below 500, the customer will be rejected. (Credit result: Rejected)
* If the credit score is between 500 points and 1000 points and the monthly income is below 5000 TL, the credit application of the customer is approved and a limit of 10.000 TL is assigned to the customer. (Credit Result: Aproved)
* If the credit score is between 500 points and 1000 points and the monthly income is above 5000 TL, the credit application of the customer is approved and a 20.000 TL limit is assigned to the customer. (Credit Result: Approved)
* If the credit score is equal to or above 1000 points, the customer is assigned a limit equal to MONTHLY INCOME * CREDIT LIMIT MULTIPLIER.The credit limit multiplier is 4 by default. (Credit Result: Approved)
* As a result of the conclusion of the credit, the relevant application is recorded in the database. Afterwards, an informative SMS is sent to the relevant phone number and the approval status information (rejection or approval), limit information is returned from the endpoint.
* A completed loan application can only be queried with an national ID number.


### Features
Informations about some endpoints is below.

Credit Application controller:
* (GET) /api/creditApplicaion/{id}: Returns credit application by id.
* (GET) /api/creditApplicaion/by-customer: Returns credit application by customer id.
* (POST) /api/creditApplicaiton/chechCreditApplicationResult: Returns credit limit and credit status by customer information.
* (GET) /api/creditApplication/checkCreditApplicationStatus: Returns credit application by customer national id.

Customer controller
* (GET) /api/customer/{id}: Returns customer by id.
* (GET) /api/customer/all :Returns all customers. 
* (GET) /api/customer/getCustomerByNationalID: Returns customer by national id

Credit Score Controller
* (GET) /api/creditScore/{id}: Returns credit score by id.
* (GET) /api/creditScore/getCreditScoreByNationalID: Returns credit score by customer national id.
* (GET) /api/creditScore/all : Returns all credit scores.


### Docker
Credit Application System project was dockerized.If you want to run it in docker, you should follow these steps.
It runs on port 8085 with Docker.
1. Comment out this code in the application properties file.(just do it when you run it with docker)
```
spring.profiles.active=docker
```
2. Build executable jar file - mvn clean-package
3. Build Docker Image
``` 
  docker build -t credit-application-system:v1 .
  ```
4. Run Docker container using the image built 
```
docker run -p 8085:8085 credit-application-system:v1
```
### Security
Endpoints are secured using Spring Web Security and Json web token.But all endpoints are permitted in web security config to be used from swagger.


### Entity Relationship Diagram
![ERD_of_DB](https://user-images.githubusercontent.com/77413677/155839101-ff4ace8b-0825-4328-8065-249901ad19d3.png)

### Swagger Photos
<img src="https://user-images.githubusercontent.com/77413677/155861271-767e3de0-3a1b-400c-ac9e-f3b8417f0114.png" width="30%"></img>
<img src="https://user-images.githubusercontent.com/77413677/155861284-cc21d3df-3a25-4094-a8c2-6185ab0eddd5.png" width="30%"></img>
<img src="https://user-images.githubusercontent.com/77413677/155890722-fb09533a-da4c-440d-889b-e2a457622f73.png" width="30%"></img>
<img src="https://user-images.githubusercontent.com/77413677/155861299-c1652e19-4d70-423f-9aae-9eeebb2af5c0.png" width="30%"></img>
