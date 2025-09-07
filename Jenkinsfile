pipeline {
    agent any

    tools {
        maven 'Maven-3.9.9'   // configure Maven in Jenkins Global Tool Config
        jdk 'jdk-17'          // configure JDK 17 in Jenkins
    }

    environment {
        BROWSER = "chrome"
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
                echo "Running Sanity Tests..."
                sh 'mvn clean test -Dtest=com.capstone.junit.SanityJUnitTest -Dbrowser=${BROWSER}'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('E2E Tests') {
            steps {
                echo "Running End-to-End Tests..."
                sh 'mvn clean test -Dtest=com.capstone.runners.EndToEndTest -Dbrowser=${BROWSER}'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Generate Cucumber Report') {
            steps {
                echo "Generating Cucumber Report..."
                // Make sure your cucumber.json is generated
                cucumber buildStatus: 'UNSTABLE',
                         fileIncludePattern: '**/target/cucumber-reports/*.json',
                         sortingMethod: 'ALPHABETICAL'
            }
        }

        stage('Archive Reports') {
            steps {
                echo "Archiving reports..."
                archiveArtifacts artifacts: 'target/cucumber-reports/*, target/surefire-reports/*, target/testng-results.xml', allowEmptyArchive: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check test reports.'
        }
    }
}
