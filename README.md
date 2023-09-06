# Textmining Utility

This utility library is used by all the other components of this textmining project.  
Consequently, it must be built and installed locally before building and installing any other component of this project.

## Local Installation and Deployment

### Prerequisites

- JDK 11
- Maven 3.6.x or higher
- MongoDB 4.4.14
- RabbitMQ 3.3.5

### Setting up MongoDB

1. Create a database named `textmining` in MongoDB.
2. Create four collections named in the `textmining` database: `tm_submissions`, `tm_annotations`, `tm_failures`, `tm_users`.
3. Provide the MongoDB connection string in the `application-utility-local.properties` file as the value of the property `spring.data.mongodb.uri`.
4. Provide the names of the MongoDB collections in the `application-utility.properties` file as the values of their corresponding properties as follows:   
   `tm.submissions.collection.name=tm_submissions`  
   `tm.annotations.collection.name=tm_annotations`  
   `tm.failures.collection.name=tm_failures`  
   
### Setting up RabbitMQ

1. Provide the RabbitMQ `addresses`, `username` and `password` in the `application-utility-local.properties` file as the values of their corresponding properties as follows:   
   `spring.rabbitmq.addresses=<Insert your RabbitMQ addresses here>`  
   `spring.rabbitmq.username=<Insert your RabbitMQ username here>`  
   `spring.rabbitmq.password=<Insert your RabbitMQ password here>`
2. Provide the names of the RabbitMQ `exchange` and `queues` in the `application-utility.properties` file as the values of their corresponding properties as follows:   
   `rabbitmq.tmExchange=tm.exchange`  
   `rabbitmq.plaintextQueue=tm_plaintext`  
   `rabbitmq.outcomeQueue=tm_outcome`  
   `rabbitmq.rawAnnotationQueue=tm_raw_annotation`  
   `rabbitmq.submissionsQueue=tm_submissions`  
   `rabbitmq.jsonForApiQueue=tm_json_for_api`  
   `rabbitmq.callbackQueue=tm_callback`  

### Building the Library Jar

Run `mvn clean install` in the root directory of the project.



