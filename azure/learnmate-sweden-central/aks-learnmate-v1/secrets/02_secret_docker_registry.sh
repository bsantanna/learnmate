#!/usr/bin/env bash

source ../env.sh

kubectl create secret docker-registry btech-registry \
  --docker-server=secure.btech.software \
  --docker-username=${DOCKER_REGISTRY_USERNAME} \
  --docker-password=${DOCKER_REGISTRY_PASSWORD}
