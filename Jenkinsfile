pipeline {
  agent any
  environment {
    IMAGE_REPO = 'mungtaregistry.azurecr.io/mungta/dev'
    IMAGE_NAME = 'accusation-service'
    IMAGE_TAG = "${env.BUILD_NUMBER}"
    //IMAGE_TAG = 'latest'
    ENVIRONMENT = 'dev'
    HELM_CHART = 'https://github.com/CarPoolService-mungta/mungta-argocd-helm.git'
  }
  stages {
    stage('Build') {
        steps {
            sh './mvnw compile'
        }
    }
    stage('Unit Test') {
        steps {
            sh './mvnw test'
        }
        post {
            always {
                junit 'target/surefire-reports/*.xml'
                step([ $class: 'JacocoPublisher' ])
            }
        }
    }
    stage('Static Code Analysis') {
        steps {
            configFileProvider([configFile(fileId: 'maven-settings', variable: 'MAVEN_SETTINGS')]) {
                sh './mvnw sonar:sonar -s $MAVEN_SETTINGS'
            }
        }
    }
    stage('Package') {
        steps {
            sh './mvnw package -DskipTests'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
    stage('Build Docker image') {
        steps {
            echo 'The build number is ${IMAGE_TAG}'
            sh 'docker build --build-arg ENVIRONMENT=${ENVIRONMENT} -t ${IMAGE_REPO}/${IMAGE_NAME}:${IMAGE_TAG} .'
        }
    }
    stage('Push Docker image') {
        steps {
            withCredentials([azureServicePrincipal('azure_service_principal')]) {
                echo '---------az login------------'
                sh '''
                az login --service-principal -u   -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID
                az account set -s $AZURE_SUBSCRIPTION_ID
                '''
                sh 'az acr login --name mungtaregistry'
                sh 'docker push ${IMAGE_REPO}/${IMAGE_NAME}:${IMAGE_TAG}'
                sh 'az logout'
            }
        }
    }
    stage('Clean Docker image') {
        steps {
            echo '---------Clean image------------'
            sh 'docker rmi ${IMAGE_REPO}/${IMAGE_NAME}:${IMAGE_TAG}'
        }
    }
    stage('Update manifest') {
        steps {
          sh """
            git config --global user.name "${GITHUB_NAME}"
            git config --global user.email "${GITHUB_EMAIL}"
            git config --global credential.helper cache
            git config --global push.default simple
          """
          git url: '${HELM_CHART}', credentialsId: 'mungta_github', branch: 'main'
          sh """
            sed -i 's/tag:.*/tag: "${IMAGE_TAG}"/g' dev/accusation/values.yaml
            git add dev/accusation/values.yaml
            git commit -m 'Update Docker image tag: ${IMAGE_TAG}'
            git push origin main
          """
        }
    }
}
