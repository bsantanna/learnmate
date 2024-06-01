#!/usr/bin/env bash

source ../env.sh

kubectl create \
  -f letsencrypt-staging-issuer.yaml

kubectl create \
  -f letsencrypt-prod-issuer.yaml
