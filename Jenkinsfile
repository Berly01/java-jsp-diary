pipeline {

    agent any
    
    tools {
        maven 'maven 3.9.6'
    }
    
    stages {
        
        stage('Clone') {
            steps {
                git branch: 'docker', url: 'https://github.com/Berly01/java-jsp-diary.git'
            }
        }
        
        stage('Code Checkout') {
            
            steps {
                checkout scmGit(branches: [[name: '*/docker']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Berly01/java-jsp-diary.git']])
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvn package -Dmaven.test.skip'
            }
        }
        
        /*
        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sonarQube') {
                    bat 'mvn verify sonar:sonar -Dmaven.test.skip'
                }
            }
        }
        */
        
        stage('Build Docker Network') {
            steps {
                bat 'docker network create my-network'
            }
        }
     
        stage('Build Docker DataBase Image') {
            steps {
                bat 'docker build -f Dockerfile.db -t db-java-jsp-diary .'
            }
        }
        
        stage('Run Docker DataBase Image') {
            steps {           
				bat 'docker run -d --name mysql-container --network my-network -p 3306:3306 db-java-jsp-diary'            
            }
        }
        

		/*
        stage('Junit Test') {
            steps {
                bat 'mvn test -Dtest=DiaryRepository,UserRepository'                          
            }
        } 
        */

        stage('Docker Build Project Image ') {
            steps {
                bat 'docker build -f Dockerfile.tc -t java-jsp-diary .'
            }
        }
        
        stage('Docker Run Project Image') {
            steps {        	
                bat 'docker run -d --name jspDiary-container --network my-network -p 8080:8080 java-jsp-diary'
            }
        }
	
		stage('wait') {
			steps {
				script {
					sleep time: 8, unit: 'SECONDS'
				}
            }
		}
	
        stage('Junit, jMeter and Selenium Test') {
            steps {
                bat 'mvn test'
            }
        }                   
    }
    
    post {        
        /*
        always {
    	    //  
            //bat 'docker logout'
            //bat 'docker network rm my-network'
    	}
        */
        failure {
            echo 'Alguna prueba falló. Deteniendo el flujo...'
        }
    }
}