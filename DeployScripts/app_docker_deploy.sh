#!/bin/bash
set -ev

cd docker/
mkdir content
wget -q "$CONTENT_PACKAGE_URL" -O content.zip
unzip -o content.zip -d content/ &> /dev/null
rm content.zip 
docker build -t osucass/ap_itemviewerservice:$BRANCH-content .
docker push osucass/ap_itemviewerservice:$BRANCH-content
