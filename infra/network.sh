#!/bin/bash

NETWORK_NAME="agendafacil-network"

if docker network ls --format '{{.Name}}' | grep -q "^$NETWORK_NAME$"; then
    echo "A rede $NETWORK_NAME já existe."
else
    docker network create "$NETWORK_NAME"
    echo "A rede $NETWORK_NAME foi criada com sucesso."
fi

docker network connect kind redis-service

containers=("backend-service" "telegram-service" "whatsapp-service" "redis-service")

for container in "${containers[@]}"
do
    if docker inspect -f '{{.State.Running}}' "$container" >/dev/null 2>&1; then
        if docker network inspect -f '{{range .Containers}}{{.Name}}{{end}}' "$NETWORK_NAME" | grep -q "\b$container\b"; then
            echo "O contêiner $container já está conectado à rede $NETWORK_NAME."
        else
            docker network connect "$NETWORK_NAME" "$container"
            echo "Conectando o contêiner $container à rede $NETWORK_NAME."
        fi
    else
        echo "O contêiner $container não está em execução."
    fi
done