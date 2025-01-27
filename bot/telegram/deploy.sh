#!/bin/bash

current_path=$(dirname "$0")

cd "$current_path"

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    terminal="konsole"
    elif [[ "$OSTYPE" == "msys"* ]]; then
    terminal="start"
fi

current_branch=$(git branch --show-current)

git pull origin "$current_branch"

docker compose build

image="pescador95/agendafacil:telegram"

docker login

echo "Adicionando tag: $image"
docker tag agendafacil $image

echo "Enviando imagem: $image"
docker push $image

echo "Imagem enviada: $image"

echo "Aplicando manifestos no kubernetes"

kubectl apply -f kubernetes/manifests/production/telegram-deployment.yaml
kubectl apply -f kubernetes/manifests/production/telegram-service.yaml
