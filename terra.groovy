pipeline {
    agent {label 'slave'}
    stages {
        stage('Pull') {
            steps {
              git branch: 'main', url: 'https://github.com/Anilbamnote/cdec-46.git'
            }
        }
        stage('Test') {
            steps {
                sh ''' 
                    cd terraform/eks
                    terraform init
                    terraform plan
                    
                    '''
            }
        }
        // stage('Deploy') {
        //     steps {
        //         sh ''' cd terraform/eks
        //             terraform init
        //             terraform apply --auto-approve'''
        //     }
        // }
          stage('Deploy') {
            steps {
                sh ''' cd terraform/eks
                    terraform init
                    terraform destroy --auto-approve'''
            }
        }
    }
}