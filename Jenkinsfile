pipeline {
    agent any
    environment {
        PAYARA_PORT='4848'
        PAYARA_HOME='/home/student/JavaTools/payara5.2020.5'
        PAYARA_HOST='localhost'
        APP_NAME='WM'
        DERBY_BIN="/home/student/JavaTools/db-derby-10.14.2.0-bin/bin"
    }
    tools {
        maven 'maven'
        jdk 'OpenJDK'
    }
    stages {
        stage('pre-build') {
            steps {
                sh 'mvn clean'
            }
            post {
                success {
                    echo 'Cleaned up old project'
                }
            }
        }
        stage('build') {
            steps {
                sh 'mvn package'
            }
            post {
                success {
                    echo 'Built application'
                }
            }
        }
        stage('pre-deploy') {
            steps {
                sh '''
                    ${DERBY_BIN}/ij<<EOF
                        connect 'jdbc:derby://localhost:1527/WM;create=true;user=WM;password=WM';
                        exit;
                    EOF
                '''
            }
            post {
                success {
                    echo 'application'
                }
            }
        }
        stage('deploy') {
            steps {
                sh '${PAYARA_HOME}/bin/asadmin --host ${PAYARA_HOST} --port ${PAYARA_PORT} deploy --force=true --name=${APP_NAME} ./target/WM-1.1.war'
            }
            post {
                success {
                    echo 'Deploy application'
                }
            }
        }
        stage('post-deploy') {
            steps {
                sh 'chmod +x ./skrypt.sh'
                sh '''./skrypt.sh'''
            }
            post {
                success {
                    echo 'application'
                }
            }
        }
    }
}
