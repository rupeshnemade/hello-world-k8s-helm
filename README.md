# Overview

This project contains the artifacts to deploy a NodeJS service via Docker, Kubernetes and Helm.

The service connects to a  MongoDB to retrieve the welcome message to return.

## Setup ##

### Install ##

1. Install [Minikube](https://kubernetes.io/docs/getting-started-guides/minikube/) (or any other Kubernetes cluster)
2. Install [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/) command line tool for Kubernetes
3. Install [Helm](https://docs.helm.sh/using_helm/), a package manager for Kubernetes based applications

### Start ###

1. Start Minikube 
```
sudo minikube start --vm-driver=none
```

2. Verify `minikube status` and `kubectl version` and `helm version` run correctly

# Helm

To deploy Helm chart see [helm/](helm/)


After Helm charts are deployed successfully, you should then be able to access the REST API as below:
- On Terminal
```
curl http://localhost/ 
```
- On Browser
```
 http://<IP of VM>/
```