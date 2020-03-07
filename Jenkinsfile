pipeline {
  agent { label 'master' }

  stages {
    stage('Build') {
      steps {
        echo 'Building...'
        sh 'scripts/build.sh'
      }
    }
    stage('Lint') {
      steps {
        echo 'Linting...'
        sh 'scripts/lint.sh'
      }
    }
    stage('Coverage') {
      steps {
        echo 'Getting coverage...'
        sh 'scripts/coverage.sh'
      }
    }
    stage('Build image') {
      when {
        branch 'master'
      }
      steps {
        echo 'Building image...'
        sh 'make'
        sh 'make push'
        sh 'make push-latest'
      }
    }
    stage('Deploy') {
      when {
        branch 'master'
      }
      steps {
        echo 'Triggering deploy job...'
        // build job: '', propagate: true, wait: true
        // Pass in the repository to get proper deploy files
        build job: 'Deploy/deploy', propagate: true, wait: true, parameters: [[$class: 'StringParameterValue', name: 'GIT_REPO', value: 'route-rating-rest-api'], [$class: 'StringParameterValue', name: 'IMAGE_TAG', value: 'latest']]
      }
    }
    stage('Smoke test') {
      when {
        branch 'master'
      }
      steps {
        echo 'Running post deploy smoke test...'
        build job: 'Test/post-release-api', propagate: true, wait: true
      }
    }
    stage('Clean') {
      when {
        branch 'master'
      }
      steps {
        echo 'Cleaning hanging images...'
        sh 'docker rmi $(docker images -q) || exit 0'
        sh 'docker rm $(docker ps -aq) || exit 0'
      }
    }
  }
  post {
    success {
      sh 'rm -rf build rest-api.jar'
    }
    failure {
      sh 'rm -rf build rest-api.jar'
    }
  }
}
