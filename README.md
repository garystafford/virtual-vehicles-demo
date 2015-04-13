## Java Microservices Demo
Java microservices architecture demo using [RestExpress](https://github.com/RestExpress), MongoDB, and AngularJS. Project is in early stage development.

According to their website, [RestExpress](https://github.com/RestExpress) composes best-of-breed open-source tools to enable quickly creating RESTful microservices that embrace industry best practices. Built from the ground-up for container-less, microservice architectures, RestExpress is the easiest way to create RESTful APIs in Java. An extremely Lightweight, Fast, REST Engine and API for Java. A thin wrapper on Netty IO HTTP handling, RestExpress lets you create performant, stand-alone REST APIs rapidly.
#### Virtual Vehicle Application Demo
The 'Virtual Vehicle' application uses an AngularJS-based web UI to call three, load-balanced, highly-available, Java EE-based microservices. Through the application, the user can create a collection of vehicles, maintain their vehicles, and use a valet service to park the their vehicles. Following good microservice architectural patters, each service has it's own MongoDB data-source.
* Vehicle Service (virtual-vehicle database)
* Maintenance Service (virtual-maintenance database)
* Valet Service (virtual-valet database)

#### Technologies Used
* [RestExpress for MongoDB](http://search.maven.org/#artifactdetails%7Ccom.strategicgains.archetype%7Crestexpress-mongodb%7C1.15%7Cmaven-archetype)
* [Netty](http://netty.io/)
* MongoDB
* [Morphia](https://github.com/mongodb/morphia)
* Java EE
* AngularJS
* HAProxy
* NetBeans, Maven, VirtualBox, Foreman, Puppet, Jenkins, and more...

