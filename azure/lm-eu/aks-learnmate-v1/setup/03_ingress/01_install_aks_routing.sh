#!/usr/bin/env bash

# Reference
# https://learn.microsoft.com/en-us/azure/aks/app-routing?tabs=default%2Cdeploy-app-default

source ../env.sh

az aks approuting enable \
  --resource-group ${AZ_RESOURCE_GROUP} \
  --name ${AZ_AKS_NAME}
