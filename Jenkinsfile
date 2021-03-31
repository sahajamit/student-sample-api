pipeline {
    agent { label 'rhelvm' }

    stages {
        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'Maven 3.3.9') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Unit Testing Stage') {
            steps {
                withMaven(maven : 'Maven 3.3.9') {
                    sh 'mvn test -Dtest=StudentApiUnitTests'
                }
            }
        }

        stage ('Integration Testing Stage') {

            steps {
                sh 'docker stop b1-coding-challenge || true'
                sh 'docker rm b1-coding-challenge || true'
                sh 'docker rmi -f b1-coding-challenge || true'
                withMaven(maven : 'Maven 3.3.9') {
                    sh 'mvn verify -Dtest=!RunCucumberTest'
                }
            }
        }

        stage ('SonarQube Analysis') {

            steps {
                withMaven(maven : 'Maven 3.3.9') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://127.0.0.1:9000 -Dsonar.login=5430b7d34e33dfc9dfafc6052849950b8401c9d0'
                }
            }
        }

        stage ('Deploy Stage') {
            steps {
                sh 'docker stop b1-coding-challenge || true'
                sh 'docker rm b1-coding-challenge || true'
                sh 'docker rmi -f b1-coding-challenge || true'
                sh 'docker build -t b1-coding-challenge .'
                sh 'docker run -d --name b1-coding-challenge --network=host -p 8081:8081 b1-coding-challenge'
                sh 'sleep 5'
            }
        }

        stage ('Cucumber Functional Testing Stage') {

            steps {
                withMaven(maven : 'Maven 3.3.9') {
                    sh 'mvn -Dtest=RunCucumberTest test'
                }

                publishHTML([allowMissing: false,
                         alwaysLinkToLastBuild: true,
                          keepAll: false,
                          reportDir: 'target/classes/static/cucumber-html-reports',
                          reportFiles: 'overview-features.html',
                          reportName: 'Cucumber Tests HTML Report',
                          reportTitles: ''])
            }
        }
    }
}