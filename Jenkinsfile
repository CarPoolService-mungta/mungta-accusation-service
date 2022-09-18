pipeline {
  agent any
  environment {
    IMAGE_REPO = 'mungtaregistry.azurecr.io/mungta/dev'
    IMAGE_NAME = 'accusation-service'
    IMAGE_TAG = ${BUILD_NUMBER}
    REGISTRY_CREDENTIALS = 'azure_service_principal'
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
            echo 'The build number is' ${BUILD_NUMBER}
            echo 'The build number2 is' $BUILD_NUMBER
            sh 'docker build --build-arg ENVIRONMENT=dev -t ${IMAGE_REPO}/${IMAGE_NAME}:${IMAGE_TAG} .'
        }
    }
    stage('Push Docker image') {
        steps {
            withCredentials([azureServicePrincipal("${REGISTRY_CREDENTIALS}")]) {
                echo '---------az login------------'
                sh '''
                az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID
                az account set -s $AZURE_SUBSCRIPTION_ID
                '''
                sh 'az acr login --name mungtaregistry'
                sh 'docker push ${IMAGE_REPO}/${IMAGE_NAME}:${IMAGE_TAG}'
                sh 'az logout'
            }
        }
    }
  }
}
