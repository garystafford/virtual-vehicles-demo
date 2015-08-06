#!/bin/sh

mkdir -p artifacts/authentication/
mkdir -p artifacts/maintenance/
mkdir -p artifacts/valet/
mkdir -p artifacts/vehicle/

cp Authentication/target/Authentication-1.0-SNAPSHOT.jar    artifacts/authentication/Authentication-1.${TRAVIS_BUILD_NUMBER}.jar
cp Authentication/target/config/test/environment.properties artifacts/authentication/environment.properties

cp Maintenance/target/Maintenance-1.0-SNAPSHOT.jar  artifacts/maintenance/Maintenance-1.${TRAVIS_BUILD_NUMBER}.jar
cp Maintenance/target/config/test/environment.properties    artifacts/maintenance/environment.properties

cp Valet/target/Valet-1.0-SNAPSHOT.jar  artifacts/valet/Valet-1.${TRAVIS_BUILD_NUMBER}.jar
cp Valet/target/config/test/environment.properties  artifacts/valet/environment.properties

cp Vehicle/target/Vehicle-1.0-SNAPSHOT.jar  artifacts/vehicle/Vehicle-1.${TRAVIS_BUILD_NUMBER}.jar
cp Vehicle/target/config/test/environment.properties    artifacts/vehicle/environment.properties