#!/usr/bin/env bash

helm upgrade \
  cert-manager jetstack/cert-manager \
  --install \
  --create-namespace \
  --wait \
  --namespace cert-manager \
  --set installCRDs=true

echo 'kubectl get svc -n "cert-manager"' | bash -x
