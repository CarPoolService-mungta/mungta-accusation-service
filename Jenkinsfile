pipeline {
  agent any
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
    stage('Package') {
        steps {
            sh "./mvnw package -DskipTests"
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
  }
}
