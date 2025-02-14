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

# Loop para percorrer todas as branches remotas
for remote_branch in $(git branch -r); do
    # Remove o prefixo "origin/" para obter o nome da branch remota
    branch_name=$(basename $remote_branch)

    # Verifica se a branch remota é do GitHub
    if [[ "$remote_branch" =~ .*github\.com.* ]]; then
        remote_branch_name="main"
    else
        remote_branch_name="master"
    fi

    # Comando para fazer pull da branch remota
    pull_command="git pull origin $branch_name:$branch_name"

    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        $terminal -e "bash -c '$pull_command; git switch $remote_branch_name; $pull_command; git switch $branch_name; exec bash'"
    elif [[ "$OSTYPE" == "msys"* ]]; then
        $terminal bash -c "$pull_command; git switch $remote_branch_name; $pull_command; git switch $branch_name; exec bash"
    fi
done
