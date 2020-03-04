void setBuildStatus(String message, String state) {
  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: env.GIT_URL],
      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "Jenkins/build-status"],
      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ]);
}

pipeline {
  agent { label 'ops' }

  stages {
    stage('Build') {
      steps {
        echo 'Building...'
        setBuildStatus('Starting build', 'PENDING')
        sh 'scripts/build.sh'
      }
    }
    stage('Lint') {
      steps {
        echo 'Linting...'
        sh './gradlew verifyGoogleJavaFormat'
      }
    }
    stage('Coverage') {
      steps {
        echo 'Getting coverage...'
        sh './gradlew jacocoTestCoverageVerification'
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
        build job: 'Deploy/deploy', propagate: true, wait: true, parameters: [[$class: 'StringParameterValue', name: 'GIT_REPO', value: 'route-rating-rest-api'], [$class: 'StringParameterValue', name: 'DEPLOY_CONFIG', value: 'deploy/deploy.json'], [$class: 'StringParameterValue', name: 'IMAGE_TAG', value: 'latest']]
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
    }}
  }
  post {
    success {
      setBuildStatus('Build succeeded', 'SUCCESS');
      sh 'make clean'
    }
    failure {
      setBuildStatus('Build failed', 'FAILURE');
      sh 'make clean'
    }
  }
}
