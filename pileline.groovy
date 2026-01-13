// pipeline {
//     agent {label 'slave'}
//     stages {
//         stage('git-pull') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
//             }
//         }
//         stage('Build') {
//             steps {
//                 sh '/opt/maven/bin/mvn clean package'
//             }
//         }
//         stage('Test') {
//             steps {
//                 withSonarQubeEnv(installationName: 'sonar',credentialsId: 'sonar-cred') {
//                    sh '/opt/maven/bin/mvn clean verify sonar:sonar '
//             }
//             timeout(10) {
 
//              }
//             waitForQualityGate true
// //                 sh '''mvn sonar:sonar \\
// //   -Dsonar.projectKey=new-studentapp \\
// //   -Dsonar.host.url=http://18.136.198.29:9000 \\
// //   -Dsonar.login=1ba13fa4dcfefe9143576f522bb4ea433f13bde9'''
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
                sh '/opt/maven/bin/mvn clean package'
            }
        }
        stage('Test') {
            steps {
                withSonarQubeEnv(installationName: 'sonar',credentialsId: 'sonar-cred') {
                   sh '/opt/maven/bin/mvn clean verify sonar:sonar '
            }
            //    sh '/opt/maven/bin/mvn clean verify sonar:sonar   -Dsonar.projectKey=new-studentapp   -Dsonar.host.url=http://18.201.46.245:9000   -Dsonar.login=ff9e6db00882d3b4203e67ca3f7e9c0dd93ef7ba'
            }
        }
         stage('Quality_gate') {
            steps {

               timeout(10) {
               echo "wait for at list 10 min to perform next action"
            }

               waitForQualityGate true
            }
        }
        //  stage('Artifact_upload') {
        //     steps {
        //         sh 'aws s3 cp target/studentapp-2.2-SNAPSHOT.war     s3://terrr-buck665598'
        //     }
        // }

        stage('Deploy') {
            steps {
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat-cred', path: '', url: 'http://108.130.178.174:8080/')], contextPath: '/', war: '**/*.war'
            }
        }
    }
}