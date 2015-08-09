#!/bin/bash

# reference: 	https://gist.github.com/domenic/ec8b0fc8ab45f39403dd

set -e # exit with nonzero exit code if anything fails

# clear and re-create the out directory
rm -rf out || exit 0;
mkdir -p out/authentication/
mkdir -p out/maintenance/
mkdir -p out/valet/
mkdir -p out/vehicle/

# compile
mvn clean install package validate -e

# copy artifacts to output directory
cp Authentication/target/Authentication-1.0-SNAPSHOT.jar    out/authentication/Authentication-1.${TRAVIS_BUILD_NUMBER}.jar
cp Authentication/config/                                   out/authentication/

cp Maintenance/target/Maintenance-1.0-SNAPSHOT.jar          out/maintenance/Maintenance-1.${TRAVIS_BUILD_NUMBER}.jar
cp Maintenance/config/                                      out/maintenance/

cp Valet/target/Valet-1.0-SNAPSHOT.jar                      out/valet/Valet-1.${TRAVIS_BUILD_NUMBER}.jar
cp Valet/config/                                            out/valet/

cp Vehicle/target/Vehicle-1.0-SNAPSHOT.jar                  out/vehicle/Vehicle-1.${TRAVIS_BUILD_NUMBER}.jar
cp Vehicle/config/                                          out/vehicle/

# go to the out directory and create a *new* Git repo
cd out
git init

# inside this git repo we'll pretend to be a new user
git config user.name "travis-ci"
git config user.email "auto-deploy@travis-ci.com"

# The first and only commit to this new Git repo contains all the
# files present with the commit message.
git add .
git commit -m "Deploy Travis CI build #${TRAVIS_BUILD_NUMBER} artifacts to GitHub"

# Force push from the current repo's master branch to the remote
# repo's build-artifacts branch. (All previous history on the gh-pages branch
# will be lost, since we are overwriting it.) We redirect any output to
# /dev/null to hide any sensitive credential data that might otherwise be exposed.

# Example values:
# export GH_TOKEN=721fd28bc24dedf553c9ecb6e6c2f137fffb8b5f
# travis encrypt GH_TOKEN=721fd28bc24dedf553c9ecb6e6c2f137fffb8b5f
# export GH_REF=github.com/garystafford/virtual-vehicles-demo.git
git push --force --quiet "https://${GH_TOKEN}@${GH_REF}" master:build-artifacts > /dev/null 2>&1
