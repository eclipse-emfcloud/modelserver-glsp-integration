pipeline {
    agent any

    tools {
        maven 'apache-maven-3.8.6'
        jdk 'openjdk-jdk11-latest'
    }

    options {
        buildDiscarder logRotator(numToKeepStr: "15")
    }

    environment {
        MAVEN_LOCAL_REPO = "/home/jenkins/.m2/repository"
        EMAIL_TO = "ndoschek+eclipseci@eclipsesource.com, eneufeld+eclipseci@eclipsesource.com"
    }

    stages {
        stage ("Build: modelserver-glsp-integration (P2)") {
            steps {
                timeout(30) {
                    sh "mvn clean verify -Pp2 -B -Dmaven.repo.local=${MAVEN_LOCAL_REPO} --settings ./.mvn/custom-settings.xml"
                }
            }
        }
        
        stage ("Build: modelserver-glsp-integration (M2)") {
            steps {
                timeout(30) {
                    sh "mvn clean verify -Pm2 -B -Dmaven.repo.local=${MAVEN_LOCAL_REPO} --settings ./.mvn/custom-settings.xml"
                }
            }
        }

        stage("Deploy (main only)") {
            when { branch "main" }
            steps {
                parallel(
                    p2: {
                        build job: "deploy-emfcloud-modelserver-glsp-integration-p2", wait: false	               
                    },
                    m2: {
                        build job: "deploy-emfcloud-modelserver-glsp-integration-m2", wait: false
                    }
                )
            }
        }
    }

    post {
        failure {
            script {
                if (env.BRANCH_NAME == 'main') {
                    echo "Build result FAILURE: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: true,
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} (#${BUILD_NUMBER}) FAILURE', to: "${EMAIL_TO}"
                }
            }
        }
        unstable {
            script {
                if (env.BRANCH_NAME == 'main') {
                    echo "Build result UNSTABLE: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: true,
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} (#${BUILD_NUMBER}) UNSTABLE', to: "${EMAIL_TO}"
                }
            }
        }
        fixed {
            script {
                if (env.BRANCH_NAME == 'main') {
                    echo "Build back to normal: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: false,
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} back to normal (#${BUILD_NUMBER})', to: "${EMAIL_TO}"
                }
            }
        }
    }
}
