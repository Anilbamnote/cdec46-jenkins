// pipeline {
//     agent any
//     stages {
//         stage('git-pull') {
//             steps {
//                 echo "pull-sucess"
//             }
//         }
//         stage('Build') {
//             steps {
//                 echo "build-sucess"
//             }
//         }
//         stage('Test') {
//             steps {
//                 echo "test sucess"
//             }
//         }
//         stage('Deploy') {
//             steps {
//                 echo "deploy sucess"
//             }
//         }
//     }
// }

pipeline {
    agent {label 'slave'}
    stages {
        stage('git-pull') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                echo "test sucess"
            }
        }
        stage('Deploy') {
            steps {
                echo "deploy sucess"
            }
        }
    }
}