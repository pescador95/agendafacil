#!/bin/bash

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )

images=("pescador95/agendafacil:quarkus" "pescador95/agendafacil:telegram" "pescador95/agendafacil:whatsapp")

cd "$parent_path"

cd ..

cd ..

cd ..

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    terminal="konsole"
    elif [[ "$OSTYPE" == "msys"* ]]; then
    terminal="start"
fi

docker compose build

for image in "${images[@]}"
do
    echo "Adicionando tag: $image"
    docker tag agendafacil $image
    echo "Enviando imagem: $image"
    docker push $image
    echo "Imagem enviada: $image"
done