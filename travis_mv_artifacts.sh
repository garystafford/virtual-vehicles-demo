#!/bin/sh

mkdir -p artifacts/authentication/
mkdir -p artifacts/maintenance/
mkdir -p artifacts/valet/
mkdir -p artifacts/vehicle/

cp Authentication/target/Authentication-1.0-SNAPSHOT.jar    artifacts/authentication/
cp Authentication/target/config/test/environment.properties artifacts/authentication/

cp Maintenance/target/Maintenance-1.0-SNAPSHOT.jar          artifacts/maintenance/
cp Maintenance/target/config/test/environment.properties    artifacts/maintenance/

cp Valet/target/Valet-1.0-SNAPSHOT.jar  					artifacts/valet/
cp Valet/target/config/test/environment.properties  		artifacts/valet/

cp Vehicle/target/Vehicle-1.0-SNAPSHOT.jar  				artifacts/vehicle/
cp Vehicle/target/config/test/environment.properties    	artifacts/vehicle/