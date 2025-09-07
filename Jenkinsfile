pipeline {
    agent any

    tools {
        maven 'Maven3'   // Make sure you have Maven installed in Jenkins (Global Tool Config)
        jdk 'JDK11'      // Ensure JDK 11 is configured in Jenkins
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
                sh "mvn clean test -Dtest=SanityJUnitTest -Dfile.encoding=UTF-8"
            }
        }

        stage('E2E Tests') {
            steps {
                echo 'Running End-to-End Cucumber/TestNG tests...'
                sh "mvn test -Dcucumber.filter.tags='@E2E' -Dfile.encoding=UTF-8"
            }
        }

        stage('Generate Cucumber Report') {
            steps {
                echo 'Generating Cucumber HTML report...'
                // If using plugin: mvn verify generates cucumber.json
                sh "mvn verify -Dfile.encoding=UTF-8"
            }
        }

        stage('Archive Reports') {
            steps {
                echo 'Archiving reports...'
                // Archive TestNG + Cucumber + Screenshots
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
