node {
   def mvnHome
   stage('Preparation') {
      git 'https://github.com/SantiagoDiazGonzalez/payroll-test-server.git'
      mvnHome = tool 'M3'
   }
   stage('SonarCloud') {
	  withSonarQubeEnv(credentialsId: '6c31c9ac315da671e4e0f34967c8ecd83aa903a5', installationName: 'SantiagoDiazGonzalez_java-projects') {
      sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar'
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
