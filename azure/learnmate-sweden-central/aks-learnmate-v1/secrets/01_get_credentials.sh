#!/usr/bin/env bash

source ../env.sh

az aks get-credentials \
  --name ${AZ_AKS_NAME}\
  --resource-group ${AZ_RESOURCE_GROUP}
