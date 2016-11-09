#!/usr/bin/env bash

# used to push local artifacts to
# run from project root directory

set -e # exit with nonzero exit code if anything fails

# clear and re-create the build-artifacts directory
rm -rf ../build-artifacts/* || exit 0;

mkdir -p ../build-artifacts/authentication/config/
mkdir -p ../build-artifacts/maintenance/config/
mkdir -p ../build-artifacts/valet/config/
mkdir -p ../build-artifacts/vehicle/config/

# compile
mvn clean install package validate -e

# copy artifacts to output directory
cp Authentication/target/Authentication-1.0-SNAPSHOT.jar  ../build-artifacts/authentication/Authentication-1.000.jar
cp -r Authentication/config/test                           ../build-artifacts/authentication/config/test

cp Maintenance/target/Maintenance-1.0-SNAPSHOT.jar        ../build-artifacts/maintenance/Maintenance-1.000.jar
cp -r Maintenance/config/test                              ../build-artifacts/maintenance/config/test

cp Valet/target/Valet-1.0-SNAPSHOT.jar                    ../build-artifacts/valet/Valet-1.000.jar
cp -r Valet/config/test                                    ../build-artifacts/valet/config/test

cp Vehicle/target/Vehicle-1.0-SNAPSHOT.jar                ../build-artifacts/vehicle/Vehicle-1.000.jar
cp -r Vehicle/config/test                                  ../build-artifacts/vehicle/config/test