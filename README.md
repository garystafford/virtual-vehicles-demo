<em>Develop a well-architected and well-documented REST API, built on a tightly integrated collection of Java EE-based microservices.</em>

__PROJECT CODE UPDATED 11-09-2016 to v3.0.0__

<a href="https://programmaticponderings.files.wordpress.com/2015/05/virtual-vehicles-architecture-4.png"><img class="aligncenter wp-image-5466 size-full" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/virtual-vehicles-architecture-4.png" alt="Virtual-Vehicles Architecture" width="660" height="332" /></a>

<span style="color:#ff0000;"><em>Note: All code available on <a href="https://github.com/garystafford/virtual-vehicle-demo">GitHub</a>. For the version of code that matches the details in this blog post, checkout v1.0 tag (after running git clone ..., run a 'git checkout tags/v1.0' command).</em></span>
<h3>Previous Post</h3>
In <a title="Building a Microservices-based REST API with RestExpress, Java EE, and MongoDB: Part 1" href="https://programmaticponderings.wordpress.com/2015/05/18/building-a-microservices-based-rest-api-with-restexpress-java-and-mongodb-part-1/">Part One</a> of this series, we introduced the microservices-based Virtual-Vehicles REST API example. The vehicle-themed Virtual-Vehicles microservices offers a comprehensive set of functionality, through a REST API, to application developers. The developers in turn, will use the Virtual-Vehicles REST API's functionality to build applications and games for their end-users.

In <a title="Building a Microservices-based REST API with RestExpress, Java EE, and MongoDB: Part 1" href="https://programmaticponderings.wordpress.com/2015/05/18/building-a-microservices-based-rest-api-with-restexpress-java-and-mongodb-part-1/">Part One</a>, we decided on the proper amount and responsibility of each microservice. We also determined the functionality of each microservice to meet the hypothetical functional and nonfunctional requirements of Virtual-Vehicles. To review, the four microservices we are building, are as follows:
<table>
<tbody>
<tr>
<td style="text-align:center;" colspan="3">
<h4>Virtual-Vehicles REST API Resources</h4>
</td>
</tr>
<tr>
<td style="text-align:center;"><strong>Microservice</strong></td>
<td style="text-align:center;"><strong>Purpose (Business Capability)</strong></td>
<td style="text-align:center;"><strong>Functions</strong></td>
</tr>
<tr>
<td><strong>Authentication
</strong></td>
<td>Manage API clients and
JWT authentication</td>
<td>
<ul>
	<li>Create a new API client (public)</li>
	<li>Read, filter, sort, count, paginate API clients (admin)</li>
	<li>Read a single API client (public)</li>
	<li>Update an existing API client (public)</li>
	<li>Delete an existing API client (admin)</li>
	<li>Create new JWT (public)</li>
	<li>Validate a JWT (internal)</li>
	<li>Service health ping (admin)</li>
</ul>
</td>
</tr>
<tr>
<td><strong>Vehicle
</strong></td>
<td>Manage virtual vehicles</td>
<td>
<ul>
	<li>Create a new vehicle (public)</li>
	<li>Read, filter, sort, count, paginate vehicles (admin)</li>
	<li>Read a single vehicle (public)</li>
	<li>Update an existing vehicle (public)</li>
	<li>Delete an existing vehicle (admin)</li>
	<li>Validate a JWT (internal)</li>
	<li>Service health ping (admin)</li>
</ul>
</td>
</tr>
<tr>
<td><strong>Maintenance
</strong></td>
<td>Manage maintenance on vehicles</td>
<td>
<ul>
	<li>Create a new maintenance record (public)</li>
	<li>Read, filter, sort, count, paginate maintenance records (admin)</li>
	<li>Read a single maintenance record (public)</li>
	<li>Update an existing maintenance record (public)</li>
	<li>Delete an existing maintenance record (admin)</li>
	<li>Validate a JWT (internal)</li>
	<li>Service health ping (admin)</li>
</ul>
</td>
</tr>
<tr>
<td><strong>Valet Parking
</strong></td>
<td>Manage a valet service to park for vehicles</td>
<td>
<ul>
	<li>Create a new valet parking transaction (public)</li>
	<li>Read, filter, sort, count, paginate valet parking transactions (admin)</li>
	<li>Read a single valet parking transaction (public)</li>
	<li>Update an existing valet parking transaction (public)</li>
	<li>Delete an existing valet parking transaction (admin)</li>
	<li>Validate a JWT (internal)</li>
	<li>Service health ping (admin)</li>
</ul>
</td>
</tr>
</tbody>
</table>
To review, the first five functions for each service are all basic CRUD operations: <code>create</code> (POST), <code>read</code> (GET), <code>readAll</code> (GET), <code>update</code> (PUT), <code>delete</code> (DELETE). The <code>readAll</code> function also has <code>find</code>, <code>count</code>, and <code>pagination</code> functionality using query parameters. Unfortunately, RestExpress does not support <code>PATCH</code> for updates. However, I have updated RestExpress' PUT HTTP methods to return the modified object in the response body instead of the nothing (status of 201 Created vs. 200 OK). See <a title="http://stackoverflow.com/a/827045/580268" href="http://stackoverflow.com/a/827045/580268">StackOverflow</a> for explanation.

All services also have an internal <code>authenticateJwt</code> function, to authenticate the JWT, passed in the HTTP request header, before performing any operation. Additionally, all services have a basic health-check function, <code>ping</code> (GET). There are only a few other functions required for our Virtual-Vehicles example, such as for creating JWTs.
<h3> Part Two Introduction</h3>
In Part Two, we will build our four Virtual-Vehicles microservices. Recall from our first post, we will be using <a href="http://search.maven.org/#artifactdetails%7Ccom.strategicgains.archetype%7Crestexpress-mongodb%7C1.15%7Cmaven-archetype">RestExpress</a>. RestExpress composes best-of-breed open-source tools to enable quickly creating RESTful microservices that embrace industry best practices. Those best-of-breed tools include Java EE, Maven, MongoDB, and Netty, among others.

In this post, we will accomplish the following:
<ul>
	<li>Create a default microservice project in NetBeans using RestExpress MongoDB Maven Archetype</li>
	<li>Understand the basic structure of a default RestExpress microservice project</li>
	<li>Review the changes made to the default RestExpress microservice project to create the Virtual-Vehicles example</li>
	<li>Compile and run the individual microservices directly from NetBeans</li>
</ul>
I used NetBeans IDE 8.0.2 on Linux Ubuntu 14.10 to build the microservices. You may also follow along in other IDE's, such as eclipse or IntelliJ, on Mac or Windows. We won't cover installing MongoDB, Maven, and Java. I'll assume if your building enterprise applications, you have the basics covered.
<h3>Using the RestExpress MongoDB Maven Archetype</h3>
All the code for this project is available on <a title="https://github.com/garystafford/virtual-vehicle-demo" href="https://github.com/garystafford/virtual-vehicle-demo">GitHub</a>. However, to really understand RestExpress, you should go through the exercise of scaffolding a new microservice using the RestExpress MongoDB Maven Archetype. You will also be able to use this default microservice project to compare and contrast to the modified versions, used in the Virtual-Vehicles example. The screen grabs below demonstrate how to create a new microservice project using the RestExpress MongoDB Maven Archetype. At the time of this post, the archetype version was restexpress-mongodb version 1.15.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-1.png"><img class="aligncenter wp-image-5492 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-1.png?w=620" alt="New Maven Project from Archetype Step 1" width="620" height="429" /></a>

<a href="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-2.png"><img class="aligncenter wp-image-5491 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-2.png?w=620" alt="New Maven Project from Archetype Step 2" width="620" height="438" /></a>

<a href="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-3.png"><img class="aligncenter wp-image-5490 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-3.png?w=620" alt="New Maven Project from Archetype Step 2" width="620" height="438" /></a>
<h3>Default Project Architecture</h3>
Reviewing the two screen grabs below (Project tab), note the key components of the RestExpress MongoDB Maven project, which we just created:
<ul>
	<li>Base Package (<em>com.example.vehicle</em>)
<ul>
	<li>Configuration class reads in environment properties (see Files tab) and instantiates controllers</li>
	<li>Constants class contains project constants</li>
	<li>Relationships class defines linking resource which aids service discoverability (HATEOAS)</li>
	<li>Main executable class</li>
	<li>Routes class defines the routes (endpoints) exposed by the service and the corresponding controller class</li>
</ul>
</li>
	<li>Model/Controllers Packages (<em>com.example.vehicle.objectid</em> and <em>.uuid</em>)
<ul>
	<li>Entity class defines the data entity – a Vehicle in this case</li>
	<li>Controller class contains the methods executed when the route (endpoint) is called</li>
	<li>Repository class defines the connection details to MongoDB</li>
	<li>Service class contains the calls to the persistence layer, validation, and business logic</li>
</ul>
</li>
	<li>Serialization Package (<em>com.example.vehicle.serialization</em>)
<ul>
	<li>XML and JSON serialization classes</li>
	<li>Object ID and UUID serialization and deserialization classes</li>
</ul>
</li>
</ul>
<a href="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-4.png"><img class="aligncenter wp-image-5489 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-4.png?w=620" alt="New Maven Project from Archetype Project View" width="620" height="355" /></a>

<a href="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-4b.png"><img class="aligncenter wp-image-5487 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-4b.png?w=620" alt="New Maven Project from Archetype Projects View" width="620" height="355" /></a>

Again, I strongly recommend reviewing each of these package's classes. To understand the core functionality of RestExpress, you must understand the relationships between RestExpress microservice's Route, Controller, Service, Repository, Relationships, and Entity classes. In addition reviewing the default Maven project, there are limited materials available on the Internet. I would recommend the <a title="https://github.com/RestExpress" href="https://github.com/RestExpress">RestExpress Website</a> on GitHub, <a title="https://groups.google.com/forum/#!forum/restexpress" href="https://groups.google.com/forum/#!forum/restexpress">RestExpress Google Group Forum</a>, and the YouTube 3-part video series, <a title="https://www.youtube.com/watch?v=XcNDRr5zaI0" href="https://www.youtube.com/watch?v=XcNDRr5zaI0">Instant REST Services with RESTExpress</a>.

<strong>Unit Tests?
</strong>Disappointingly, the current RestExpress MongoDB Maven Archetype sample project does not come with sample JUnit unit tests. I am temped to start writing my own unit tests, if I decided to continue to use the RestExpress microservices framework for future projects.
<h3>Properties Files</h3>
Also included in the default RestExpress MongoDB Maven project is a Java properties file (<code>environment.properties</code>). This is displayed in the Files tab, as shown below. The default properties file is located in the 'dev' environment config folder. Later, we will create an additional properties file for our production environment.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/default-properties-file.png"><img class=" wp-image-5545 size-large aligncenter" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/default-properties-file.png?w=660" alt="Default Properties File" width="660" height="378" /></a>

<strong>Ports</strong>
Within the 'dev' environment, each microservice is configured to start on separate ports  (i.e. <code>port = 8581</code>). Feel free to change the service's port mappings if they conflict with previously configured components running on your system. Be careful when changing the Authentication service's port, 8587, since this port is also mapped in all other microservices using the <code>authentication.port</code> property (<code>authentication.port = 8587</code>). Make sure you change both properties, if you change Authentication service's port mapping.

<strong>Base URL</strong>
Also, in the properties files is the <code>base.url</code> property. This property defines the URL the microservice's endpoints will be expecting calls from, and making internal calls between services. In our post's example, this property in the 'dev' environment is set to localhost (<code>base.url = http://localhost</code>). You could map an alternate hostname from your hosts file (<code>/etc/hosts</code>). We will do this in a later post, in our 'prod' environment, mapping the <code>base.url</code> property to Virtual-Vehicles (<code>base.url = http://virtual-vehicles.com</code>). In the 'dev' environment properties file, MongoDB is also mapped to <code>localhost</code> (i.e. <code>mongodb.uri = mongodb://virtual-vehicles.com:27017/virtual_vehicle</code>).

<strong>Metrics Plugin and Graphite</strong>
RestExpress also uses the properties file to hold configuration properties for <a title="https://github.com/RestExpress/PluginExpress/wiki/Metrics-Plugin" href="https://github.com/RestExpress/PluginExpress/wiki/Metrics-Plugin">Metrics Plugin</a> and <a title="http://graphite.wikidot.com/" href="http://graphite.wikidot.com/">Graphite</a>. The Metrics Plugin and Graphite are both first class citizens of RestExpress. Below is the copy of the Vehicles service <code>environment.properties</code> file for the 'dev' environment. Note, the Metrics Plugin and Graphite are both disabled in the 'dev' environment.

```bash
# Default is 8081
port = 8581

# Port used to call Authentication service endpoints
authentication.port = 8587

# The size of the executor thread pool
# (that can handle blocking back-end processing).
executor.threadPool.size = 20

# A MongoDB URI/Connection string
# see: http://docs.mongodb.org/manual/reference/connection-string/
mongodb.uri = mongodb://localhost:27017/virtual_vehicle

# The base URL, used as a prefix for links returned in data
base.url = http://localhost

#Configuration for the MetricsPlugin/Graphite
metrics.isEnabled = false
#metrics.machineName =
metrics.prefix = web1.example.com
metrics.graphite.isEnabled = false
metrics.graphite.host = graphite.example.com
metrics.graphite.port = 2003
metrics.graphite.publishSeconds = 60
```

<h3>Choosing a Data Identification Method</h3>
RestExpress offers two identification models for managing data, the MongoDB ObjectId and a Universally Unique Identifier (UUID). MongoDB uses an ObjectId to uniquely identify a <a title="http://docs.mongodb.org/manual/reference/glossary/#term-document" href="http://docs.mongodb.org/manual/reference/glossary/#term-document">document</a> within a <a class="reference internal" href="http://docs.mongodb.org/manual/reference/glossary/#term-collection">collection</a>. The <a title="http://docs.mongodb.org/manual/reference/glossary/#term-objectid" href="http://docs.mongodb.org/manual/reference/glossary/#term-objectid">ObjectId</a> is a special 12-byte <a class="reference internal" href="http://docs.mongodb.org/manual/reference/glossary/#term-bson">BSON</a> type that guarantees uniqueness of the document within the collection. Alternately, you can use the UUID identification model. The UUID identification model in RestExpress uses a UUID, instead of a MongoDB ObjectId. The UUID also contains <code>createdAt</code> and <code>updatedAt</code> properties that are automatically maintained by the persistence layer. You may stick with ObjectId, as we will in the Virtual-Vehicles example, or choose the UUID. If you will use multiple database engines for your own projects, using UUID will give you a universal identification method.
<h3>Project Modifications</h3>
There are many small code changes that differentiate our Virtual-Vehicles microservices from the default RestExpress Maven Archetype project. Most changes are superficial, nothing was changed about how RestExpress functions. The following are the more obvious changes you will note between the screen grabs, above, showing the default project versus the screen grabs, below, showing the final Virtual-Vehicles microservices from GitHub:
<ul>
	<li>Remove all packages, classes, and code references to the UUID identification methods (example uses ObjectId)</li>
	<li>Rename several classes for convenience (dropped use of word 'Entity')</li>
	<li>Add the Utilities (<em>com.example.utilities</em>) and Authentication (<em>com.example.authenticate</em>) packages</li>
</ul>
<a href="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-5.png"><img class="aligncenter wp-image-5488 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-5.png?w=660" alt="Final Virtual-Vehicles Microservices Projects" width="660" height="378" /></a>

<a href="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-5b.png"><img class="aligncenter wp-image-5486 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/new-maven-project-from-archetype-5b.png?w=660" alt="Final Virtual-Vehicles Microservices Projects" width="660" height="378" /></a>
<h3>MongoDB</h3>
Following one of the key principles of microservices, which was mentioned in the first post, <strong>Decentralized Data Management</strong>, each microservice will have its own instance of a MongoDB database associated with it. The below diagram shows each service and its corresponding database, collection, and fields.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/vehicle-micsrv-uml-2-21.png"><img class="aligncenter wp-image-5529 size-full" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/vehicle-micsrv-uml-2-21.png" alt="Virtual-Vehicles Database Diagram" width="660" height="380" /></a>
From the MongoDB shell, we can observe the individual instances of the four microservice's databases.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/mongodb-show-databases.png"><img class="aligncenter wp-image-5512 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/mongodb-show-databases.png?w=620" alt="MongoDB Virtual-Vehicles Databases" width="620" height="423" /></a>

In the case of the Vehicle microservice, the associated MongoDB database is <code>virtual_vehicle</code>. This database contains a single collection, <code>vehicles</code>. While the properties file defines the database name, the Vehicles entity class defines the collection name, using the <code>org.mongodb.morphia.annotations</code> classes annotation functionality.

```java
@Entity("vehicles")
public class Vehicle
        extends AbstractMongodbEntity
        implements Linkable {

    private int year;
    private String make;
    private String model;
    private String color;
    private String type;
    private int mileage;
...
```

Looking at the <code>virtual_vehicle</code> database in the MongoDB shell, we see that the sample document's fields corresponds to the Vehicle entity classes properties.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/mongodb-collection-document.png"><img class="aligncenter wp-image-5511 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/mongodb-collection-document.png?w=620" alt="MongoDB vehicles Document" width="620" height="423" /></a>
Each of the microservice's MongoDB databases are configured in the <code>environments.properties</code> file, using the <code>mongodb.uri</code> property. In the 'dev' environment we use <code>localhost</code> as our host URL (i.e. <code>mongodb.uri = mongodb://localhost:27017/virtual_vehicle</code>).
<h3>Authentication and JSON Web Tokens</h3>
The three microservices, Vehicle, Valet, and Maintenance, are almost identical. However, the Authentication microservice is unique. This service is called by each of the other three services, as well as also being called directly. The Authentication service provides a very basic level of authentication using JSON Web Tokens (JWT), pronounced '<em>jot</em>'.

Why do we want authentication? We want to confirm that the requester using the Virtual-Vehicles REST API is the actual registered API client they are who they claim to be. JWT allows us to achieve this requirement with minimal effort.

According to jwt.io, '<em>a JSON Web Token is a compact URL-safe means of representing claims to be transferred between two parties. The claims in a JWT are encoded as a JSON object that is digitally signed using JSON Web Signature (JWS).</em>' I recommend reviewing the <a title="http://self-issued.info/docs/draft-ietf-oauth-json-web-token.html" href="http://self-issued.info/docs/draft-ietf-oauth-json-web-token.html">JWT draft standard</a> to fully understand the structure, creation, and use of JWTs.

<strong>Virtual-Vehicles Authentication Process</strong>
There are different approaches to implementing JWT. In our Virtual-Vehicles REST API example, we use the following process for JWT authentication:
<ol>
	<li>Register the new API client by supplying the application name and a shared secret (one time only)</li>
	<li>Receive an API key in response (one time only)</li>
	<li>Obtain a JWT using the API key and the shared secret (each user session or renew when the previous JWT expires)</li>
	<li>Include the JWT in each API call</li>
</ol>
In our example, we are passing four JSON fields in our set of claims. Those fields are the issuer ('iss'), API key, expiration ('exp'), and the time the JWT was issued ('ait'). Both the 'iss' and the 'exp' claims are defined in the Authentication service's <code>environment.properties</code> file (<code>jwt.expire.length</code> and <code>jwt.issuer</code>).

Expiration and Issued date/time use the JWT standard recommended "Seconds Since the <a title="http://www.epochconverter.com/" href="http://www.epochconverter.com/">Epoch</a>".  The default expiration for a Virtual-Vehicles JWT is set to an arbitrary 10 hours from time the JWT was issued (<code>jwt.expire.length = 36000</code>). That amount, 36,000, is equivalent to 10 hours x 60 minutes/hour x 60 seconds/minute.

<strong>Decoding a JWT</strong>
Using the <a title="http://jwt.io/" href="http://jwt.io/">jwt.io</a> site's JT Debugger tool, I have decoded a sample JWT issued by the Virtual-Vehicles REST API, generated by the Authentication service. Observe the three parts of the JWT, the JOSE Header, Claims Set, and the <a title="http://tools.ietf.org/html/rfc7515" href="http://tools.ietf.org/html/rfc7515">JSON Web Signature (JWS)</a>.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/decoded-jwt1.png"><img class="aligncenter wp-image-5559 size-large" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/decoded-jwt1.png?w=620" alt="Decoded JWT" width="620" height="476" /></a>

The JWT's header indicates that our JWT is a JWS that is MACed using the <a title="http://en.wikipedia.org/wiki/Hash-based_message_authentication_code" href="http://en.wikipedia.org/wiki/Hash-based_message_authentication_code">HMAC</a> SHA-256 algorithm. The shared secret, passed by the API client, represents the HMAC secret <a class="mw-redirect" title="Cryptographic key" href="http://en.wikipedia.org/wiki/Cryptographic_key">cryptographic key</a>. The secret is used in combination with the <a title="Cryptographic hash function" href="http://en.wikipedia.org/wiki/Cryptographic_hash_function">cryptographic hash function</a> to calculate the <a title="Message authentication code" href="http://en.wikipedia.org/wiki/Message_authentication_code">message authentication code</a> (MAC). In the example below, note how the API client's shared secret is used to validate our JWT's JWS.

<strong>Sequence Diagrams of Authentication Process</strong>
Below are three sequence diagrams, which detail the following processes: API client registration process, obtaining a new JWT, and a REST call being authenticated using the JWT. The end-user of the API self-registers their application using the Authentication service, and receives back an API key. The API key is unique to that client.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/jwt-sequence-diagram-21.png"><img class="aligncenter wp-image-5528 size-full" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/jwt-sequence-diagram-21.png" alt="Register API Client (Authentication Service)" width="660" height="323" /></a>

The end-user application then uses the API key and the shared secret to receive a JWT from the Authentication service.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/jwt-sequence-diagram-31.png"><img class="aligncenter wp-image-5527 size-full" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/jwt-sequence-diagram-31.png" alt="Requesting a JWT (Authentication Service)" width="478" height="365" /></a>After receiving the JWT, the end-user application passes the JWT in header of each API request. The JWT is validated by calling the Authentication service. If the JWT is valid, the request is fulfilled. If not valid, a '401 Unauthorized' status is returned.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/jwt-sequence-diagram-41.png"><img class="aligncenter wp-image-5526 size-full" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/jwt-sequence-diagram-41.png" alt="API Call with JWT Passed in Header (Vehicle Service)" width="660" height="343" /></a><strong>JWT Validation</strong>
The JWT draft standard recommends how to <a title="http://self-issued.info/docs/draft-ietf-oauth-json-web-token.html#Validating" href="http://self-issued.info/docs/draft-ietf-oauth-json-web-token.html#Validating">validate</a> a JWT. Our Virtual-Vehicles Authentication microservice uses many of those criteria to validate the JWT, which include:
<ol>
	<li>API Key - Retrieve API client's shared secret from MongoDB, using API key contained in JWT's claims set (secret is returned, therefore API key is valid)</li>
	<li>Algorithm - confirm the algorithm ('alg'), found in the JWT Header, which used to encode JWT, was 'HS256' (HMAC SHA-256)</li>
	<li>Valid JWS - Use the client's shared secret from #1 above, decode HMAC SHA-256 encrypted JWS</li>
	<li>Expiration - confirm JWT is not expired ('exp')</li>
</ol>
<h3>Inter-Service Communications</h3>
By default, the RestExpress Archetype project does not offer an example of communications between microservices. Service-to-service communications for microservices is most often done using the same HTTP-based REST calls used to by our Virtual-Vehicles REST API. Alternately, a <a title="http://java.dzone.com/articles/exploring-message-brokers" href="http://java.dzone.com/articles/exploring-message-brokers">message broker</a>, such as RabbitMQ, Kafka, ActiveMQ, or Kestrel, is often used. Both methods of communicating between microservices, referred to as 'inter-service communication mechanisms' by <a title="http://www.infoq.com/articles/microservices-intro" href="http://www.infoq.com/articles/microservices-intro">InfoQ</a>, have their pros and cons. The <a title="http://www.infoq.com/articles/microservices-intro" href="http://www.infoq.com/articles/microservices-intro">InfoQ</a> website has an excellent microservices post, which discusses the topic of service-to-service communication.

For the Virtual-Vehicles microservices example, we will use HTTP-based REST calls for inter-service communications. The primary service-to-service communications in our example, is between the three microservices, Vehicle, Valet, and Maintenance, and the Authentication microservice. The three services validate the JWT passed in each request to a CRUD operation, by calling the Authentication service and passing the JWT, as shown in the sequence diagram, above. This is done using an HTTP GET request to the Authentication service's <code>.../jwts/{jwt}</code> endpoint. The Authentication service's method, called by this endpoint, minus some logging and error handling, looks like the following:

```java
public boolean authenticateJwt(Request request, String baseUrlAndAuthPort) {
    String jwt, output, valid = "";

    try {
        jwt = (request.getHeader("Authorization").split(" "))[1];
    } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
        return false;
    }

    try {
        URL url = new URL(baseUrlAndAuthPort + "/jwts/" + jwt);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            return false;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        while ((output = br.readLine()) != null) {
            valid = output;
        }

        conn.disconnect();
    } catch (IOException e) {
        return false;
    }

    return Boolean.parseBoolean(valid);
}
```

Primarily, we are using the <code>java.net</code> and <code>java.io</code> packages, along with the <code>org.restexpress.Request</code> class to build and send our HTTP request to the Authentication service. Alternately, you could use just the <code>org.restexpress</code> package to construct request and handle the response. This same basic method structure shown above can be used to create unit tests for your service's endpoints.
<h3>Health Ping</h3>
Each of the Virtual-Vehicles microservices contain a <code>DiagnosticController</code> in the <code>.utilities</code> package. In our example, we have created a <code>ping()</code> method. This simple method, called through the <code>.../utils/ping</code> endpoint, should return a <code>200 OK</code> status and a boolean value of 'true', indicating the microservice is running and reachable. This route's associated method could not be simpler:

```java
public void ping(Request request, Response response) {
    response.setResponseStatus(HttpResponseStatus.OK);
    response.setResponseCode(200);
    response.setBody(true);
}
```

The ping health check can even be accessed with a simple curl command, <code>curl localhost:8581/vehicles/utils/ping</code>.

In a real-world application, we would add additional health checks to all services, providing additional insight into the health and performance of each microservice, as well as the service's dependencies.
<h3>API Documentation</h3>
A well written RESTful API will not require extensive documentation to understand the API's operations. Endpoints will be discoverable through linking (see Response Body links section in below example). API documentation should provide HTTP method, required headers and URL parameters, expected response body and response status, and so forth.

An API should be documented before any code is written, similar to TDD. The API documentation is the result of a clear understanding of requirements. The API documentation should make the coding even easier, since the documentation serves as a further refinement of the requirements. The requirements are an architectural plan for the microservice's code structure.

<strong>Sample Documentation</strong>
Below, is a sample of the Virtual-Vehicles REST API documentation. It details the function responsible for creating a new API client. The documentation provides a brief description of the function, the operation's endpoint (URI), HTTP method, request parameters, expected response body, expected response status, and even a view of the MongoDB collection's document for a new API client.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/virtual-vehicles-api-guide.png"><img class="aligncenter wp-image-5540 size-full" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/virtual-vehicles-api-guide.png" alt="Virtual-Vehicles API Guide" width="660" height="652" /></a>

You can download a PDF version of the Virtual-Vehicles RESTful API documentation on <a title="https://github.com/garystafford/virtual-vehicle-demo/blob/master/Virtual-Vehicles_API_Guide.pdf" href="https://github.com/garystafford/virtual-vehicle-demo/blob/master/Virtual-Vehicles_API_Guide.pdf">GitHub</a> or review the <a title="https://docs.google.com/document/d/1QQoafw7i3ExhFKLHVwuylpin23aXUL3hnb3f4mpTng8/edit?usp=sharing" href="https://docs.google.com/document/d/1QQoafw7i3ExhFKLHVwuylpin23aXUL3hnb3f4mpTng8/edit?usp=sharing">source document</a> on Google Docs. It contains general notes about the API, and details about every one of the API's operations.
<h3>Running the Individual Microservices</h3>
For development and testing purposes, the easiest way to start the microservices is in NetBeans using the <code>Run</code> command. The <code>Run</code> command executes the Maven <code>exec</code> goal. Based on the <code>DEFAULT_ENVIRONMENT</code> constant in the <code>org.restexpress.util</code> Environment class, RestExpress will use the 'dev' environment's <code>environment.properties</code> file, in the project's <code>/config/dev</code> directory.

Alternately, you can use the RestExpress project's recommended command from a terminal prompt to start each microservice from its root directory (<code>mvn exec:java -Dexec.mainClass=test.Main -Dexec.args="dev"</code>). You can also use this command to switch from the 'dev' to 'prod' environment properties (<code>-Dexec.args="prod"</code>).

You may use a variety of commands to confirm all the microservices are running. I prefer something basic like <code>sudo netstat -tulpn | grep 858[0-9]</code>. This will find all the ports within the 'dev' port range. For more in-depth info, you can use a command like <code>ps -aux | grep com.example | grep -v grep</code>

<a href="https://programmaticponderings.files.wordpress.com/2015/05/microservices-running.png"><img class="aligncenter size-full wp-image-5581" src="https://programmaticponderings.files.wordpress.com/2015/05/microservices-running.png" alt="Microservices Running" width="642" height="438" /></a>
<h3>Part Three: Testing our Services</h3>
We now have a copy of the Virtual-Vehicles project pulled from GitHub, a basic understanding of how RestExpress works, and our four microservices running on different ports. In <a href="https://programmaticponderings.wordpress.com/2015/06/05/building-a-microservices-based-rest-api-with-restexpress-java-ee-and-mongodb-part-3/">Part Three</a> of this series, we will take them for a drive (<em>get it?</em>). There are many ways to test our service's endpoints. One of my favorite tools is Postman. we will explore how to use several tools, including Postman, and our API documentation, to test our microservice's endpoints.

<a href="https://programmaticponderings.files.wordpress.com/2015/05/postman-preview.png"><img class="aligncenter wp-image-5557 size-full" style="border:0 solid #ffffff;" src="https://programmaticponderings.files.wordpress.com/2015/05/postman-preview.png" alt="Postman Preview" width="660" height="406" /></a>
