#!/usr/bin/env bash

export AZ_AKS_NAME="aks-learnmate-v1"
export AZ_CLIENT_ID="?"
export AZ_KEY_VAULT_NAME="key-vault-learnmate-v1"
export AZ_KEY_VAULT_SOPS_KEY_NAME="aks-learnmate-v1-sops-key"
export AZ_KEY_VAULT_SOPS_KEY_ID=$(\
  az keyvault key show \
    -n ${AZ_KEY_VAULT_SOPS_KEY_NAME} \
    --vault-name ${AZ_KEY_VAULT_NAME} \
    --query key.kid -o tsv \
)
export AZ_RESOURCE_GROUP="learnmate-sweden-central"

export DOCKER_REGISTRY_USERNAME="?"
export DOCKER_REGISTRY_PASSWORD="?"
