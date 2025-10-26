pipeline {
    agent any

    environment {
        IMAGE_NAME = "springboot-app"
        CONTAINER_NAME = "springboot-app"
        GITHUB_REPO = "https://github.com/Shivangran/springboot-jenkins-demo.git"
        GIT_BRANCH = "main"
        GIT_CREDENTIALS = "github-creds"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out code from GitHub..."
                git branch: "${GIT_BRANCH}", credentialsId: "${GIT_CREDENTIALS}", url: "${GITHUB_REPO}"
            }
        }

        stage('Build Fat Jar') {
            steps {
                echo "Building fat jar..."
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Archive Artifact') {
            steps {
                echo "Archiving fat jar..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('Stop Existing Container') {
            steps {
                echo "Stopping any running container..."
                sh """
                    if [ \$(docker ps -q -f name=${CONTAINER_NAME}) ]; then
                        docker stop ${CONTAINER_NAME}
                        docker rm ${CONTAINER_NAME}
                    fi
                """
            }
        }

        stage('Run Docker Container') {
            steps {
                echo "Starting container..."
                sh "docker run -d -p 8080:8080 --name ${CONTAINER_NAME} ${IMAGE_NAME}"
            }
        }

        stage('Verify') {
            steps {
                echo "Listing running containers..."
                sh "docker ps"
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed. Check the logs above."
        }
    }
}
