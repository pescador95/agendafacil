#.PHONY: setup cluster create-namespace apply-crds wait-for-webhook gateway-route teardown

#setup: cluster create-namespace apply-crds wait-for-webhook gateway-route

.PHONY: setup cluster create-namespace ingress teardown

setup: cluster create-namespace ingress

cluster:
	@kind create cluster --config kubernetes/config/config.yaml
	
create-namespace:
	@kubectl create namespace agendafacil	

ingress:
	@kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
	@kubectl wait --namespace ingress-nginx \
		--for=condition=ready pod \
		--selector=app.kubernetes.io/component=controller \
		--timeout=300s

apply-crds:
	@kubectl apply -f https://github.com/kubernetes-sigs/gateway-api/releases/download/v0.5.1/standard-install.yaml
	@kubectl wait --for=condition=Established --timeout=60s crd/gatewayclasses.gateway.networking.k8s.io
	@kubectl wait --for=condition=Established --timeout=60s crd/gateways.gateway.networking.k8s.io
	@kubectl wait --for=condition=Established --timeout=60s crd/httproutes.gateway.networking.k8s.io

wait-for-webhook:
	@kubectl wait --for=condition=ready pod -l app.kubernetes.io/component=webhook -n gateway-system --timeout=300s

gateway-route:
	@kubectl apply -f kubernetes/gateway/gateway.yaml
	@kubectl wait --namespace agendafacil \
		--for=condition=ready pod \
		--selector=gateway.networking.k8s.io/controller \
		--timeout=300s

teardown:
	@kind delete clusters agendafacil-cluster
	@kubectl delete namespace agendafacil