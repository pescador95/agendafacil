#!/bin/bash
echo "Expondo serviços..."
kubectl port-forward service/frontend-service 80:80
kubectl port-forward service/backend-service 3000:3000
kubectl port-forward service/whatsapp-service 4000:4000
kubectl port-forward service/telegram-service 5000:5000
kubectl port-forward service/redis-service 6379:6379
echo "Serviços expostos."