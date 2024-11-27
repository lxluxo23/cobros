pipeline {
    agent any
    stages {
        stage('Build and Deploy') {
            steps {
                script {
                    sh 'docker rm -f contenedor-cobros-backend || true'
                    sh 'docker-compose up --build -d'
                }
            }
        }
    }
}
