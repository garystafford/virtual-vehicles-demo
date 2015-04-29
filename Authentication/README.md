A Minimal RestExpress Server
============================
A template RestExpress project that contains minimal external dependencies.

To run the project:
	mvn clean package exec:java

To create a project deployable assembly (zip file):
	mvn clean package
	mvn assembly:single
