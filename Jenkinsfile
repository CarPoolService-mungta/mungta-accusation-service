node {
  stages {
    stage('Build') {
        steps {
            sh './mvnw compile'
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
