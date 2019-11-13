node {
   def mvnHome
   stage('Preparation') {
      git 'https://github.com/SantiagoDiazGonzalez/payroll-test-server.git'
      mvnHome = tool 'M3'
   }
   stage('Build') {
      withEnv(["MVN_HOME=$mvnHome"]) {
        sh 'cd server && "$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
      }
   }
   stage('SonarCloud') {
	  withEnv(["MVN_HOME=$mvnHome"]) {
	    withSonarQubeEnv('sonarcloud.io') {
          sh 'cd server && "$MVN_HOME/bin/mvn" org.sonarsource.scanner.maven:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=c0c135e534e0350a8ef29df7ace8f9fd0a101771'
	    }
	  }
   }
   stage('Results') {
      archiveArtifacts 'server/target/*.jar'
   }
}
