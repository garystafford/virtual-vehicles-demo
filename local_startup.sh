#!/usr/bin/env bash

# run from project root directory

# kill previously running processes
kill $(ps aux | grep 'java -jar' | grep -v 'grep' | awk '{print $2}')

set -e # exit with nonzero exit code if anything fails

# clear and re-create the build-artifacts directory
rm -rf build-artifacts || exit 0;

mkdir -p build-artifacts/authentication/config/
mkdir -p build-artifacts/maintenance/config/
mkdir -p build-artifacts/valet/config/
mkdir -p build-artifacts/vehicle/config/

# compile
mvn clean install package validate -e

# copy artifacts to output directory
cp Authentication/target/Authentication-1.0-SNAPSHOT.jar  build-artifacts/authentication/Authentication-1.0-SNAPSHOT.jar
cp -r Authentication/config/dev/                          build-artifacts/authentication/config/dev/

cp Maintenance/target/Maintenance-1.0-SNAPSHOT.jar        build-artifacts/maintenance/Maintenance-1.0-SNAPSHOT.jar
cp -r Maintenance/config/dev/                             build-artifacts/maintenance/config/dev/

cp Valet/target/Valet-1.0-SNAPSHOT.jar                    build-artifacts/valet/Valet-1.0-SNAPSHOT.jar
cp -r Valet/config/dev/                                   build-artifacts/valet/config/dev/

cp Vehicle/target/Vehicle-1.0-SNAPSHOT.jar                build-artifacts/vehicle/Vehicle-1.0-SNAPSHOT.jar
cp -r Vehicle/config/dev/                                 build-artifacts/vehicle/config/dev/

# go to the build-artifacts directory and create a *new* Git repo
cd build-artifacts/authentication/ && \
    java -jar Authentication-1.0-SNAPSHOT.jar dev

cd build-artifacts/maintenance/ && \
    java -jar Maintenance-1.0-SNAPSHOT.jar dev

cd build-artifacts/valet/ && \
    java -jar Valet-1.0-SNAPSHOT.jar dev

cd build-artifacts/vehicle/ && \
    java -jar Vehicle-1.0-SNAPSHOT.jar dev

# list running services
ps aux | grep 'java -jar' | grep -v 'grep'