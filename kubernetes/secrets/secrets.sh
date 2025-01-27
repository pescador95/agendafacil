#!/bin/bash

NAMESPACE="agendafacil"

kubectl create secret generic database-user --from-literal=database-user="$DATABASE_USER" --namespace=$NAMESPACE
kubectl create secret generic database-password --from-literal=database-password="$DATABASE_PASSWORD" --namespace=$NAMESPACE
kubectl create secret generic database-url --from-literal=database-url="$DATABASE_URL" --namespace=$NAMESPACE

kubectl create secret generic email-user --from-literal=email-user="$EMAIL_USER" --namespace=$NAMESPACE
kubectl create secret generic email-password --from-literal=email-password="$EMAIL_PWD" --namespace=$NAMESPACE

kubectl create secret generic telegram-token --from-literal=telegram-token="$TELEGRAM_TOKEN" --namespace=$NAMESPACE
kubectl create secret generic telegram-release-token --from-literal=telegram-release-token="$TELEGRAM_RELEASE_TOKEN" --namespace=$NAMESPACE
