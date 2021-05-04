pipeline {
    agent any

    tools {
        maven 'apache-maven-latest'
        jdk 'openjdk-jdk11-latest'
    }

    stages {
        stage ('Build: Eclipse-based (P2)') {
            steps {
                // ignore test failures since we parse the test results afterwards
                sh 'mvn clean verify -Pp2 --batch-mode package' 
            }
        }

        stage('Deploy') {
            when { branch 'master' }
            steps {
                p2: {
                    build job: 'deploy-emfcloud-modelserver-glsp-integration-p2', wait: false
                }
            }
        }
    }
}
