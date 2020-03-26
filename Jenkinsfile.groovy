pipeline {
    agent none

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
        skipStagesAfterUnstable()
    }
    triggers {
        git(triggerOnPush: true,
                triggerOnMergeRequest: false,
                triggerOnAcceptedMergeRequest: false,
                branchFilterType: 'All',
                pendingBuildName: env.JOB_NAME,
                cancelPendingBuildsOnUpdate: true,
                secretToken: gitlabSecretToken
        )
    }

    environment {
        projectName = 'HelloWorld'
        projectUrl = 'https://github.com/rupeshnemade/hello-world-k8s-helm.git'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git(
                        url: projectUrl,
                        branch: 'master'
                )
            }
            post {
                failure {
                    cleanWs()
                }
            }

        }

        stage('Docker Build') {
            steps {
                dir('app/db') {
                   
                    sh """docker build -t  nemadern/mongodb:latest ."""
                    echo "MongoDB Docker build success"
                }
				dir('app/server') {
                   
                    sh """docker build -t  nemadern/helloapi:latest ."""
                    echo "Node hello API Docker build success"
                }
            }
        }

        stage('Docker Hub push') {
            steps {
                script {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding',
						credentialsId: 'dockerhub',
						usernameVariable: 'DOCKER_HUB_USER',
						passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
			
						sh 'docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD}'
                        sh 'docker push nemadern/mongodb:latest'
                        sh 'docker push nemadern/helloapi:latest'

                        echo "Docker image successfully pushed to Docker Hub"

                        echo "Deleting docker image from workspace"
                        sh 'docker rmi nemadern/mongodb:latest'
                        sh 'docker rmi nemadern/helloapi:latest'
                    }
                }
            }
        }
		
        stage('Deploy-Dev') {
            }
            steps {
                echo "Deploying to Dev environment"
				
				sh 'kubectl create namespace dev'
                sh 'helm list --all-namespaces'
				sh 'helm upgrade --install hello-world-mongo ./helm/ --values=./helm/dev/values.yaml -n dev'
            }
            post {
                failure {
                    cleanWs()
                }
            }

        }

        stage('Deploy-Prod') {
            }
            steps {
                echo "Deploying to Prod environment"
				
				sh 'kubectl create namespace prod'
                sh 'helm list --all-namespaces'
				
				sh 'helm upgrade --install hello-world-mongo ./helm/ --values=./helm/prod/values.yaml -n prod'
            }
            post {
                failure {
                    cleanWs()
                }
            }

        }
    }
}