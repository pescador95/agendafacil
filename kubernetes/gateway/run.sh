#!/bin/bash

current_path=$(dirname "$0")

cd $current_path

echo "Subindo gateway API"
kubectl apply -f "gateway.yaml"