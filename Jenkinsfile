node {
   def mvnHome
   def customImage
   stage('Preparation') {
      git 'https://github.com/SantiagoDiazGonzalez/payroll-test-server.git'
      mvnHome = tool 'M3'
   }
   stage('Build') {
      withEnv(["MVN_HOME=$mvnHome"]) {
        sh 'cd server && "$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
		customImage = docker.build("santiagodiazgonzalez/payroll-santiago")
      }
   }
   stage('Sonar Cloud') {
	 withEnv(["MVN_HOME=$mvnHome"]) {
	    sh 'cd server && "$MVN_HOME/bin/mvn" verify sonar:sonar -Dsonar.projectKey=SantiagoDiazGonzalez_payroll-test-server -Dsonar.organization=santiagodiazgonzalez -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=876af135544926d07b8504fbca77d79158032a3d -Dmaven.test.failure.ignore=true'
	}
   }
   stage('Push Image') {
	 docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
        customImage.push()
    }
   }
   stage('Results') {
      archiveArtifacts 'server/target/*.jar'
      junit '**/target/surefire-reports/TEST-*.xml'
   }
}
