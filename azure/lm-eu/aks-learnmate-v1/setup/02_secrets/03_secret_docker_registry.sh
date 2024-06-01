#!/usr/bin/env bash

source ../env.sh

kubectl create secret docker-registry btech-registry \
  --namespace ${AZ_AKS_APP_NAMESPACE} \
  --docker-server=secure.btech.software \
  --docker-username=${DOCKER_REGISTRY_USERNAME} \
  --docker-password=${DOCKER_REGISTRY_PASSWORD}
