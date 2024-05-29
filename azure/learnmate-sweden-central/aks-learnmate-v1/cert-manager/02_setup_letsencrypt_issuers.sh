#!/usr/bin/env bash

source ../env.sh

kubectl create -n "${AZ_AKS_APP_NAMESPACE}" -f letsencrypt-staging-issuer.yaml

kubectl create -n "${AZ_AKS_APP_NAMESPACE}" -f letsencrypt-prod-issuer.yaml
