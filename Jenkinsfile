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
      agent {
        docker {
          image 'dacr/jenkins-docker-agent-sbt'
          args '-v $HOME/.ivy2:/home/sbt/.ivy2 -v $HOME/.m2:/home/sbt/.m2'
        }
      }
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

  post {
    success {
      slackSend(
        failOnError:false, color:'good',
        message: "${env.JOB_NAME} build SUCCESS ! <${env.RUN_DISPLAY_URL}|pipeline status>",
      )
    }
    failure {
      slackSend(
        failOnError:false, color:'#FF0000',
        message:"${env.JOB_NAME} build FAILURE ! <${env.RUN_DISPLAY_URL}|pipeline status>",
      )
    }
  }

}
