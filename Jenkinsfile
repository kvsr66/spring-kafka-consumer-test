pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      steps {
        bat 'mvn spring-boot:run'
      }
    }

  }
}