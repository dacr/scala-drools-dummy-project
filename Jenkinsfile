pipeline {
  agent any
  environment { 
    LANG = 'C'
  }
  triggers {
    pollSCM('H/2 * * * *')
  }
  options {
    buildDiscarder(logRotator(numToKeepStr:'20'))
  }
  stages {

    // ----------------------------- BUILD
    stage('build') {
      agent { docker { image 'dacr/jenkins-docker-agent-sbt' } }
      steps {
        sh 'sbt assembly'
      }
      post {
        success {
          archive 'target/**/dummy.jar'
          junit 'target/junitresults/**/*.xml'
	  stash name:'exe', includes:'target/**/dummy.jar'
        }
      }
    }

    // ----------------------------- tryit
    stage('tryit') {
      agent { docker { image 'dacr/jenkins-docker-agent-sbt' } }
      steps {
        unstash 'exe'
        sh 'java -jar target/*/dummy.jar'
      }
    }

  }

}
