#!/usr/bin/env bash

source ./env.sh

kubectl create secret generic secret-api-web \
  --from-literal LEARNMATE_SEARCH_HOST=? \
  --from-literal LEARNMATE_SEARCH_USERNAME=? \
  --from-literal LEARNMATE_SEARCH_PASSWORD=? \
  --from-literal LEARNMATE_OAUTH_CLIENT_ID=? \
  -o yaml \
  --dry-run=client \
  > secret.encoded.yaml

sops --encrypt \
  --encrypted-regex '^(data|stringData)$' \
  --azure-kv ${AZ_KEY_VAULT_SOPS_KEY_ID} \
  secret.encoded.yml > secret-api-web.encrypted.yaml && \
  rm secret.encoded.yaml

sops --decrypt secret-api-web.encrypted.yaml | kubectl apply -f -
