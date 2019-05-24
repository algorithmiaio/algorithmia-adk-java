
pipeline {
  agent {
    docker {
      image 'algorithmiahq/sbt-builder:latest'
      args '-v $HOME/.m2:/root/.m2 -v $HOME/.sbt:/root/.sbt --net=host'
      registryUrl 'https://index.docker.io/v1/'
      registryCredentialsId 'Dockerhub-Jenkins'
    }
  }

  stages {
    stage('Build') {
      steps {
        sh 'sbt clean compile'
      }
    }

    stage('Test') {
      steps {
        sh 'sbt test'
      }
    }
  }

}