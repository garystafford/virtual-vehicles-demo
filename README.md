## Microservices / RESTful API Integration with MEAN Stack Demo
**_This project is still in early stage development_**

Develop a well-architected and well-documented RESTful API example, built on a tightly-integrated collection of Java EE-based microservices. Build a MEANRAD Stack application, which relies on the RESTful API for a majority of functionality. Ensure the services are highly-available, secure, and scalable.

#### Virtual Vehicles Microservices / RESTful API
The 'Virtual Vehicles' RESTful API (api.virtual-vehicles.com) exposes a vehicle-themed collection of load-balanced, highly-available, Java EE, RestExpress-based microservices. The RESTful microservices follow much of current microservice architectural patterns. Each service has it's own MongoDB datasource. Services include:
* Vehicle Service (virtual_vehicle MongoDB database)
* Maintenance Service (virtual_maintenance MongoDB database)
* Valet Service (virtual_valet MongoDB database)
* Authentication Service (virtual_authentication MongoDB database)

#### MEAN Stack Application
Leveraging the 'Virtual Vehicles' RESTful API, we will build an web-based minimally-viable web-based application, 'My Virtual Cars' (myvirtualcars.com). This application, built on the MEAN Stack, will allow users to create a collection of virtual vehicles, maintain and service their vehicles, and use a virtual valet service to park the their vehicles.

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
 * JWT for authentication
* NetBeans and WebStorm IDEs
* Git, GitHub SCM
* VirtualBox, Vagrant, Foreman, Puppet, Jenkins for CI and CD
* HAProxy for load-balancing

#### About RestExpress
According to their website, [RestExpress](https://github.com/RestExpress) composes best-of-breed open-source tools to enable quickly creating RESTful microservices that embrace industry best practices. Built from the ground-up for container-less, microservice architectures, RestExpress is the easiest way to create RESTful APIs in Java. An extremely Lightweight, Fast, REST Engine and API for Java. A thin wrapper on Netty IO HTTP handling, RestExpress lets you create performant, stand-alone REST APIs rapidly.

#### Project Goals
* Immediate Goals
 * Create 'Virtual Vehicles' RestExpress-based microservices
 * Use RESTful HTTP-based service-to-service communication for now.
 * Add JWT authentication for all RESTful calls.
 * Test all service operations in Postman or similar RESTful tool.
 * Document Services and API completely.
* Short-term Goals
 * Create MEAN Stack based, 'My Virtual Cars' application, leveraging 'Virtual Vehicles' microservices.
 * Create JUnit unit tests in project. New RestExpress archetype does not have jUnit tests.
 * Implement Jenkins for automated CI and CD of services and application.
* Medium-term Goals
 * User Foreman, Puppet, Vagrant, and VirtualBox to fully provision environment with HAProxy, Apache Web Server, Java, and microservices.
 * Manually deploy (4) HAProxy load-balanced, RestExpress-based microservices services running on (4) nodes (VMs).
 * Ensure services start on restart.
 * Ensure each load-balanced service pool has it's own NoSQL MongoDB databases. (4) total databases.
* Long-term Goals
 * Implement messaging bus (RabbitMQ) service-to-service communication (paintVehicle() example).
 * Implement [Graphite](http://graphite.readthedocs.org/en/latest/overview.html) enterprise-scale monitoring tool. Graphite integration native to RestExpress.