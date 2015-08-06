#!/bin/bash

# reference: 	https://gist.github.com/domenic/ec8b0fc8ab45f39403dd

set -e # exit with nonzero exit code if anything fails

# clear and re-create the out directory
rm -rf out || exit 0;
#mkdir out;
mkdir -p out/authentication/
mkdir -p out/maintenance/
mkdir -p out/valet/
mkdir -p out/vehicle/

# run our compile script, discussed above
#./compile.sh
mvn clean install package validate -e

cp Authentication/target/Authentication-1.0-SNAPSHOT.jar    out/authentication/
cp Authentication/target/config/test/environment.properties out/authentication/

cp Maintenance/target/Maintenance-1.0-SNAPSHOT.jar          out/maintenance/
cp Maintenance/target/config/test/environment.properties    out/maintenance/

cp Valet/target/Valet-1.0-SNAPSHOT.jar                      out/valet/
cp Valet/target/config/test/environment.properties          out/valet/

cp Vehicle/target/Vehicle-1.0-SNAPSHOT.jar                  out/vehicle/
cp Vehicle/target/config/test/environment.properties        out/vehicle/

# go to the out directory and create a *new* Git repo
cd out
git init

# inside this git repo we'll pretend to be a new user
git config user.name "garystafford"
git config user.email "garystafford@rochester.rr.com"

# The first and only commit to this new Git repo contains all the
# files present with the commit message "Deploy to GitHub Pages".
git add .
git commit -m "Deploy latest build artifacts to GitHub"

# Force push from the current repo's master branch to the remote
# repo's gh-pages branch. (All previous history on the gh-pages branch
# will be lost, since we are overwriting it.) We redirect any output to
# /dev/null to hide any sensitive credential data that might otherwise be exposed.
git push --force --quiet "https://${GH_TOKEN}@${GH_REF}" master:build-artifacts > /dev/null 2>&1
