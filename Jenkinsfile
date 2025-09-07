pipeline {
    agent any

    tools {
        maven 'mvn'   // from Jenkins Global Tool Config
        jdk 'java'    // from Jenkins Global Tool Config
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/YandamuriAkshay/ParaBank_project.git'
            }
        }

        stage('Sanity Tests') {
            steps {
                echo 'Running Sanity JUnit tests...'
                bat "mvn clean test -Dtest=SanityJUnitTest -Dfile.encoding=UTF-8"
            }
        }

        stage('E2E Tests') {
            steps {
                echo 'Running End-to-End Cucumber/TestNG tests...'
                bat "mvn test -Dcucumber.filter.tags='@E2E' -Dfile.encoding=UTF-8"
            }
        }

        stage('Generate Cucumber Report') {
            steps {
                echo 'Generating Cucumber HTML report...'
                bat "mvn verify -Dfile.encoding=UTF-8"
            }
        }

        stage('Archive Reports') {
            steps {
                echo 'Archiving reports...'
                junit 'target/surefire-reports/*.xml'
                cucumber buildStatus: 'UNSTABLE', fileIncludePattern: '**/cucumber*.json', trendsLimit: 10
                archiveArtifacts artifacts: 'target/**', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            cleanWs()
        }
    }
}
