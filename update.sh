#!/bin/bash

update() {
    echo "Iniciando trabalhos no modo de ambiente escolhido: $1"
    echo " "
    echo "Atualizando o ambiente..."
    echo " "
    echo " "
    echo "atualizando secrets..."
        ./kubernetes/secrets/secrets.sh
    echo " "
    echo "atualizando manifestos de deployments e services..."
    if [ "$1" == "dev" ]; then
        ./kubernetes/manifests/up-dev.sh <<< "redis,backend,frontend,telegram,grafana,prometheus"
    elif [ "$1" == "prod" ]; then
        ./kubernetes/manifests/up.sh <<< "redis,backend,frontend,telegram,whatsapp,grafana,prometheus"
    else
        echo "Modo de ambiente inválido."
    fi
    echo " "
    echo "atualizando ingress..."
    ./kubernetes/ingress/run.sh
    
#    echo "atualizando gateway api..."
#    ./kubernetes/gateway/run.sh

    echo " "
    echo "Atualização do ambiente concluída."
}

echo " "
echo "Modos de ambiente disponíveis: "
echo " "
echo "dev"
echo "prod"
echo " "
echo "Por favor, escolha o modo de ambiente a atualizar:"
echo " "
read input_string
echo " "

if [ "$input_string" != "dev" ] && [ "$input_string" != "prod" ]; then
    echo "Modo de ambiente inválido."
    exit 1
fi

update "$input_string"