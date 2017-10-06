#!/bin/bash
set -ev

cd docker/
docker build -f Dockerfile.code -t osucass/ap_itemviewerservice:$BRANCH .
docker push osucass/ap_itemviewerservice:$BRANCH

docker build -f Dockerfile.code --build-arg contextName=iatpreview -t osucass/ap_itemviewerservice:$BRANCH-iat .
docker push osucass/ap_itemviewerservice:$BRANCH-iat