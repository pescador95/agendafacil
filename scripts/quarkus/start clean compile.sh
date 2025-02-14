#!/bin/bash

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )

cd "$parent_path"

cd ..

cd ..

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    terminal="konsole"
    elif [[ "$OSTYPE" == "msys"* ]]; then
    terminal="start"
fi

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    $terminal -e "bash -c 'cd backend;mvn clean compile quarkus:dev;exec bash'"
    elif [[ "$OSTYPE" == "msys"* ]]; then
    $terminal bash -c "cd backend;mvn clean compile quarkus:dev;exec bash"
fi