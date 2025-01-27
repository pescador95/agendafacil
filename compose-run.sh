#!/bin/bash

current_path=$(dirname "$0")

cd "$current_path"

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    terminal="konsole"
    elif [[ "$OSTYPE" == "msys"* ]]; then
    terminal="start"
fi

arch=$(uname -m)

current_branch=$(git branch --show-current)

echo "Executando o script na branch: $current_branch"
echo "Build and Run - Projeto: agendafacil... ##############"

source ../infra/network.sh

echo "Executando docker compose down..."
echo "Apagando imagens e containers..."

docker compose -p redis-service down --volumes
docker compose -p redis-service rm -f

docker image rm pescador95/agendafacil:redis

echo "Criando imagens e containers..."

docker compose -p redis-service -f docker-compose.yml build

echo "Executando docker compose up..."

docker compose -p redis-service -f docker-compose.yml up -d

echo "Adicionando o Container a rede agendafacil-network..."

docker network connect agendafacil-network redis-service

echo "Containers em execução: ##################################"

docker ps

echo "##########################################################"
echo "docker compose Executado com sucesso!"
echo "Containers em execução: ##################################"
docker ps
echo "##########################################################"
echo "docker compose Executado com sucesso!"