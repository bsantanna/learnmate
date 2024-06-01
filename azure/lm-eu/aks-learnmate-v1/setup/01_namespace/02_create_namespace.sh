#!/usr/bin/env bash

source ../env.sh

kubectl create namespace "${AZ_AKS_APP_NAMESPACE}"

kubectl config set-context --current --namespace="${AZ_AKS_APP_NAMESPACE}"
