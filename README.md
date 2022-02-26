# Credit Application System
It is a restful credit application system that receives credit application requests and returns the credit result to the customer according to the relevant criteria. It is written using the Spring Boot framework.

### Requirements
For Building and running the application belows are required:

* Spring Boot 2.6.3
* JDK 8
* Maven 3.8.3
* PostgreSQL
* Swagger 3.0.0
* RabbitMQ (Requires Erlang)
* Lombok
* JUnit

### Functional Requirements
* New customers can be created in the system, existing customers can be updated or deleted.
* If the credit score is below 500, the customer will be rejected. (Credit result: Rejected)
* If the credit score is between 500 points and 1000 points and the monthly income is below 5000 TL, the credit application of the customer is approved and a limit of 10.000 TL is assigned to the customer. (Credit Result: Aproved)
* If the credit score is between 500 points and 1000 points and the monthly income is above 5000 TL, the credit application of the customer is approved and a 20.000 TL limit is assigned to the customer. (Credit Result: Approved)
* If the credit score is equal to or above 1000 points, the customer is assigned a limit equal to MONTHLY INCOME * CREDIT LIMIT MULTIPLIER.The credit limit multiplier is 4 by default. (Credit Result: Approved)
* As a result of the conclusion of the credit, the relevant application is recorded in the database. Afterwards, an informative SMS is sent to the relevant phone number and the approval status information (rejection or approval), limit information is returned from the endpoint.
* A completed loan application can only be queried with an national ID number.
###  Entities
* [CreditApplication](https://github.com/vildntn/Credit-Application-System/blob/main/src/main/java/com/example/CreditApplicationSystem/model/entity/CreditApplication.java)
* [CreditScore](https://github.com/vildntn/Credit-Application-System/blob/main/src/main/java/com/example/CreditApplicationSystem/model/entity/CreditScore.java)
* [Customer](https://github.com/vildntn/Credit-Application-System/blob/main/src/main/java/com/example/CreditApplicationSystem/model/entity/Customer.java)
* [CreditApplicationDto](https://github.com/vildntn/Credit-Application-System/blob/main/src/main/java/com/example/CreditApplicationSystem/model/dto/CreditApplicationDto.java)

### Frontend
Frontend side is developed using the react library.
* Here is the link to the Frontend project.
[Credit-Application-Frontend](https://github.com/vildntn/Credit-Application-Frontend)
### Helper Project with RabbitMQ
After applying for a credit to the customer, the simulation of sending an sms was made with a microservice architecture.
Communication between the two architectures takes place with RabbitMQ.

* Here is the link to the Helper project.
[Credit-Application-Helper](https://github.com/vildntn/Credit-Application-Helper)