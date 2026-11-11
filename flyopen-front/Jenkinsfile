pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Comprobar Node y npm') {
            steps {
                bat 'node -v'
                bat 'npm -v'
            }
        }

        stage('Instalar dependencias') {
            steps {
                bat 'npm ci'
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                bat 'npm test -- --watch=false'
            }
        }

        stage('Compilar Angular') {
            steps {
                bat 'npm run build'
            }
        }

        stage('Analizar con SonarCloud') {
            steps {
                script {
                    def scannerHome = tool 'SonarScanner'

                    withSonarQubeEnv('SonarCloud') {
                        withCredentials([
                            string(
                                credentialsId: 'sonarcloud-token',
                                variable: 'SONAR_TOKEN'
                            )
                        ]) {
                            bat """
                                "${scannerHome}\\bin\\sonar-scanner.bat" ^
                                -Dsonar.projectKey=herneshem_flyopen-front ^
                                -Dsonar.organization=herneshem ^
                                -Dsonar.host.url=https://sonarcloud.io ^
                                -Dsonar.token=%SONAR_TOKEN%
                            """
                        }
                    }
                }
            }
        }
    }
}