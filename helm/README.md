# Hello World Service

This Helm chart provides an example deployment of the Hello World Service. 

The back end service consists of a simple NodeJS REST API to retrieve a message from a Mongo database.

A Deployment is used to create a Deployment for the REST API. 

Following objects are used to deploy MongoDB:
- PersistentVolume
- PersistentVolumeClaim
- ClusterIP
- StatefulSets

A Service is used to create a gateway to the back end REST API pods running in the deployment.

An Ingress is used to rewrite the paths of the service and offer externally through one common service.
To enable ingress on minikube execute below command:
```
minikube addons enable ingress
```
This chart creates infrastructure for Dev & Prod environment. Before deploying these environment with Helm, first crate Kuberntes namespace with below command:-
```
kubectl create namespace dev
kubectl create namespace prod
```
Verify namespaces are created with below command
```
helm list --all --all-namespaces
```

Deploy this chart to minikube cluster for Dev & Prod environment with below commands:
```
cd helm/
helm upgrade --install hello-world-mongo . --values=./dev/values.yaml -n dev
helm upgrade --install hello-world-mongo . --values=./prod/values.yaml -n prod
```
To verify everything is deployed on Kubernetes cluster run below kubectl commands:-
```
kubectl get pod -n dev
kubectl get svc -n dev
kubectl get ingress -n dev

kubectl get pod -n prod
kubectl get svc -n prod
kubectl get ingress -n prod
```