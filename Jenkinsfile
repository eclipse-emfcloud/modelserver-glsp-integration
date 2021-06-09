pipeline {
    agent any

    tools {
        maven 'apache-maven-latest'
        jdk 'openjdk-jdk11-latest'
    }

    stages {
        stage ('Build: Eclipse-based (P2)') {
            steps {
                sh 'mvn clean verify -Pp2 -B' 
            }
        }
        
        stage ('Build: Plain Maven (M2)') {
        	steps {
           		sh 'mvn clean verify -Pm2 -B' 
            }
        }

        stage('Deploy (main only)') {
            when { branch 'main' }
            steps {
                parallel(
                    p2: {
                        build job: 'deploy-emfcloud-modelserver-glsp-integration-p2', wait: false	               
                    },
                    m2: {
                        build job: 'deploy-emfcloud-modelserver-glsp-integration-m2', wait: false
                    }
                )
            }
        }
    }
}
