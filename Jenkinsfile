pipeline {
    agent any

    stages {
        stage('Crear imagen y desplegar') {
            steps {
                script {
                    echo 'Construyendo y desplegando el contenedor'
                    sh 'docker-compose up --build -d'
                }
            }
        }
    }
}
