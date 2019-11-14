node {
   def mvnHome
   def image
   stage('Preparation') {
      git 'https://github.com/SantiagoDiazGonzalez/payroll-test-server.git'
      mvnHome = tool 'M3'
   }
   stage('Build') {
      withEnv(["MVN_HOME=$mvnHome"]) {
        sh 'cd server && "$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
		image = docker.build("santiagodiazgonzalez/payroll-santiago")
      }
   }
   stage('Sonar Cloud') {
	 withEnv(["MVN_HOME=$mvnHome"]) {
	    sh 'cd server && "$MVN_HOME/bin/mvn" verify sonar:sonar -Dsonar.projectKey=SantiagoDiazGonzalez_payroll-test-server -Dsonar.organization=santiagodiazgonzalez -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=876af135544926d07b8504fbca77d79158032a3d -Dmaven.test.failure.ignore=true'
	}
   }
   stage('Push Image') {
	 docker.withRegistry('https://hub.docker.com/repository/docker/santiagodiazgonzalez/payroll-santiago', 'dockerhub') {
        customImage.push()
    }
	withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'password', usernameVariable: 'user')]) {
            sh 'docker login --username ${user} --password ${password}'
            sh label: '', script: 'docker push santiagodiazgonzalez/payroll-santiago'
        }
   }
   stage('Results') {
      archiveArtifacts 'server/target/*.jar'
      junit '**/target/surefire-reports/TEST-*.xml'
   }
}
