#!/usr/bin/env bash

source ../env.sh

az keyvault set-policy \
  -n ${AZ_KEY_VAULT_NAME} \
  -g ${AZ_RESOURCE_GROUP} \
  --spn ${AZ_CLIENT_ID} \
  --key-permissions encrypt decrypt

az keyvault key create \
  -n ${AZ_KEY_VAULT_SOPS_KEY_NAME} \
  --vault-name ${AZ_KEY_VAULT_NAME} \
  --ops encrypt decrypt \
  --protection software
