node {
    def WORKSPACE = "/home/ubuntu/download/jenkins_workspace/workspace/Movie_Backend"
    def dockerImageTag = "movieapi${env.BUILD_NUMBER}"
    def buildNumber = "${env.BUILD_NUMBER}"

    try {
        stage('Clone Repo') {
            //for display purposes
            //Get some code from a GitHub repository
            git url: 'https://github.com/kwantke/Movie_Backend.git',
                branch: 'master'
        }
        stage('Build docker') {
            //build npm
            //sh "sudo npm run build"
            dockerImage = docker.build("movieapi:${env.BUILD_NUMBER}")
            //sh 'docker build -t springboot-deploy:${buildNumber} .'
        }
        stage('Deploy docker') {
            echo "Docker Image Tag Name: ${dockerImageTag}"
            sh "docker stop movieapi || true && docker rm movieapi || true"
            sh "docker run --name movieapi -d -p 9045:8080 movieapi:${env.BUILD_NUMBER}"
        }
    } catch(e){
        throw e
    }
}