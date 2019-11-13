node {
   def mvnHome
   stage('Preparation') {
      git 'https://github.com/SantiagoDiazGonzalez/payroll-test-server.git'
      mvnHome = tool 'M3'
   }
   stage('SonarCloud') {
	  withSonarQubeEnv(credentialsId: '876af135544926d07b8504fbca77d79158032a3d', installationName: 'SantiagoDiazGonzalez_payroll-test-server') {
      sh '"$MVN_HOME/bin/mvn" org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar'
	  }
   }
   stage('Build') {
      withEnv(["MVN_HOME=$mvnHome"]) {
        sh 'cd server && "$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
      }
   }
   stage('Results') {
      archiveArtifacts 'server/target/*.jar'
   }
}
