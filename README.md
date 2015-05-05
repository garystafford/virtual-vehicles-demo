## Java Microservices Integration with MEAN Stack Demo
**_This project is still in early stage development_**

Java-based microservices integration with MEAN Stack demonstration. Uses the MEAN Stack, [RestExpress](https://github.com/RestExpress), Java, [Netty](http://netty.io/), JWT, and HAProxy.

#### Virtual Vehicles Microservices / API
The 'Virtual Vehicles' API (virtual-vehicles.com) exposes a vehicle-themed collection of load-balanced, highly-available, Java EE, RestExpress-based microservices. The RESTful microservices follow current microservice architectural patterns. Each service has it's own MongoDB datasource.  
Leveraging the 'Virtual Vehicles' API, we will build an web-based minimally-viable application, 'My Virtual Cars' (myvirtualcars.com). This application, built on the MEAN Stack, will allow users to create a collection of virtual vehicles, maintain and service their vehicles, and use a virtual valet service to park the their vehicles.
* Vehicle Service (virtual_vehicle Mongo database)
* Maintenance Service (virtual_maintenance Mongo database)
* Valet Service (virtual_valet Mongo database)
* Authentication Service (virtual_authentication database)

#### Technologies Used
* [RestExpress](http://search.maven.org/#artifactdetails%7Ccom.strategicgains.archetype%7Crestexpress-mongodb%7C1.15%7Cmaven-archetype)
* [Netty](http://netty.io/)
* Java EE
* Maven
* MEAN Stack
 * MongoDB
 * AngularJS
 * Express
 * Node.js
* NetBeans, WebStorm IDEs
* Git, GitHub SCM
* VirtualBox, Vagrant, Foreman, Puppet, Jenkins for CI and CD
* HAProxy

#### About RestExpress
According to their website, [RestExpress](https://github.com/RestExpress) composes best-of-breed open-source tools to enable quickly creating RESTful microservices that embrace industry best practices. Built from the ground-up for container-less, microservice architectures, RestExpress is the easiest way to create RESTful APIs in Java. An extremely Lightweight, Fast, REST Engine and API for Java. A thin wrapper on Netty IO HTTP handling, RestExpress lets you create performant, stand-alone REST APIs rapidly.
taf
#### Project Goals
* Short-term
 * Create 'Virtual Vehicles' RestExpress-based microservices with RESTful Web API with HTTP-based service-to-service communication.
 * Document API and ensure services testable in Postman or similar tool.
 * Create MEAN Stack based, 'My Virtual Cars' application, leveraging 'Virtual Vehicles' microservices.
 * Create JUnit unit tests in project. New REstExpress archetype does not have jUnit tests.
 * Implement Jenkins for automated CI and CD of services and application.
* Medium-term
 * User Foreman, Puppet, Vagrant, and VirtualBox to fully provision environment with HAProxy, Apache Web Server, Java, and microservices.
 * Manually deploy (4) HAProxy load-balanced, RestExpress-based microservices services running on (4) nodes (VMs).
 * Ensure services start on restart.
 * Ensure each load-balanced service pool has it's own NoSQL MongoDB databases. (4) total databases.
* Long-term
 * Implement messaging bus (RabbitMQ) service-to-service communication (paintVehicle() example).
 * Implement [Graphite](http://graphite.readthedocs.org/en/latest/overview.html) enterprise-scale monitoring tool. Graphite integration native to RestExpress.

 #### Client Feature Supported by RESTful API
 * buyVehicle() --> POST to Vehicle Service
 * sellVehicle(oid) --> DELETE to Vehicle Service
 * viewVehicleCollection --> GET to Vehicle Service
 * driveVehicle(oid) --> PUT to Vehicle Service
 * washVehicle(oid) --> POST to Maintenance Service
 * changeOil(oid) --> POST to Maintenance Service
 * paintVehicle(oid) --> POST to Maintenance Service
 * viewMaintenanceRecordsByVehicle(oid) --> GET to Maintenance Service
 * parkVehicle(oid) --> POST to Valet Service
 * retieveVehicle(oid) --> PUT to Valet Service
 * viewValetTransactionsByVehicle(oid) --> GET to Valet Service