#!/usr/bin/env bash

source ../env.sh

az keyvault create \
  -n ${AZ_KEY_VAULT_NAME} \
  -g ${AZ_RESOURCE_GROUP} \
  -l swedencentral
