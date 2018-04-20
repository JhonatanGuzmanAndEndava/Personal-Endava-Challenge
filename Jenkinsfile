pipeline {
    agent any

    stages {
        stage('Prepare') {
            steps {
                echo 'Preparing..'
                sh 'rm -Rf'
                git branch: 'back', credentialsId: 'auth1', url: 'https://github.com/JhonatanGuzmanAndEndava/Personal-Endava-Challenge.git'

            }
        }
        stage('Test') {
            steps {
                sh 'sudo -S chmod -R 777 readers-nest-backend/bookclubs'
                sh 'sudo -S chmod -R 777 readers-nest-backend/books'
                sh 'sudo -S chmod -R 777 readers-nest-backend/users'
                sh 'sudo -S chmod -R 777 readers-nest-graphql'
                
                sh 'cd readers-nest-backend/bookclubs && sudo mvn test'
                sh 'cd readers-nest-backend/books && sudo mvn test'
                sh 'cd readers-nest-backend/users && sudo mvn test'

            }
        }
        stage('Build') {
            steps {
                echo 'Building dockerfiles'
                
                sh 'cd readers-nest-backend/bookclubs && sudo mvn clean install -Dmaven.test.skip'
                sh 'cd readers-nest-backend/books && sudo mvn clean install -Dmaven.test.skip'
                sh 'cd readers-nest-backend/users && sudo mvn clean install -Dmaven.test.skip'
                
                sh 'scp -r readers-nest-graphql ubuntu@172.31.67.209:/home/ubuntu/'
                sh 'scp -r readers-nest-backend/bookclubs ubuntu@172.31.67.209:/home/ubuntu/'
                sh 'scp -r readers-nest-backend/books ubuntu@172.31.66.141:/home/ubuntu/'
                sh 'scp -r readers-nest-backend/users ubuntu@172.31.66.141:/home/ubuntu/'
                
                sh 'ssh ubuntu@172.31.67.209 sudo docker build -t graphql:1.0 /home/ubuntu/readers-nest-graphql'
                sh 'ssh ubuntu@172.31.67.209 sudo docker build -t bookclubs:1.0 /home/ubuntu/bookclubs'
                sh 'ssh ubuntu@172.31.66.141 sudo docker build -t books:1.0 /home/ubuntu/books'
                sh 'ssh ubuntu@172.31.66.141 sudo docker build -t users:1.0 /home/ubuntu/users'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                
                sh 'ssh ubuntu@172.31.67.209 sudo docker run --name graphqlcont --rm -d -p 4000:4000 graphql:1.0'
                sh 'ssh ubuntu@172.31.67.209 sudo docker run --name bookclubscont --rm -d -p 9002:9002 bookclubs:1.0'
                sh 'ssh ubuntu@172.31.66.141 sudo docker run --name bookscont --rm -d -p 9001:9001 books:1.0'
                sh 'ssh ubuntu@172.31.66.141 sudo docker run --name userscont --rm -d -p 9000:9000 users:1.0'
                
                input "Do you want to terminate microservice?"
                
                sh 'ssh ubuntu@172.31.67.209 sudo docker stop graphqlcont'
                sh 'ssh ubuntu@172.31.67.209 sudo docker stop bookclubscont'
                sh 'ssh ubuntu@172.31.66.141 sudo docker stop bookscont'
                sh 'ssh ubuntu@172.31.66.141 sudo docker stop userscont'
            }
        }
    }
}