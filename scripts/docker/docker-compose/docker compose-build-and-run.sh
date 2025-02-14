#!/bin/bash

current_path=$(dirname "$0")

cd "$current_path"

cd ..

cd ..

cd ..

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    terminal="konsole"
    elif [[ "$OSTYPE" == "msys"* ]]; then
    terminal="start"
fi

current_branch=$(git branch --show-current)

echo "Executando o script na branch: $current_branch"
echo "Build and Run - Projeto: agendafacil... ##############"
echo "Executando docker compose down..."
echo "Apagando imagens e containers..."

docker compose -p agendafacil down --volumes
docker compose -p agendafacil rm -f

if [[ $(docker images -q pescador95/agendafacil:db) ]]; then
    docker image rm pescador95/agendafacil:db
fi
docker image rm pescador95/agendafacil:quarkus
docker image rm pescador95/agendafacil:telegram
docker image rm pescador95/agendafacil:whatsapp
docker image rm pescador95/agendafacil:redis

echo "Criando imagens e containers..."
docker compose -p agendafacil -f "docker-compose.yml build
echo "Executando docker compose up..."
docker compose -p agendafacil -f "docker-compose.yml up -d
echo "Containers em execução: ##################################"
docker ps
echo "##########################################################"
read -n 1 -s -r -p "docker compose Executado com sucesso! Pressione qualquer tecla para sair."