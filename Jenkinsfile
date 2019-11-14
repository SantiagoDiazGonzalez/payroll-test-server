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
	//	image = docker.build("santiagodiazgonzalez/payroll-santiago")
      }
   }
   stage('Sonar Cloud') {
	 //withEnv(["MVN_HOME=$mvnHome"]) {
	 //   sh 'cd server && "$MVN_HOME/bin/mvn" verify sonar:sonar -Dsonar.projectKey=SantiagoDiazGonzalez_payroll-test-server -Dsonar.organization=santiagodiazgonzalez -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=876af135544926d07b8504fbca77d79158032a3d -Dmaven.test.failure.ignore=true'
	//}
   }
   stage('Push Image') {
     //docker.withRegistry('', 'dockerhub') {
	 //  image.push()
	 //}
   }
   stage('Results') {
      //archiveArtifacts 'server/target/*.jar'
      //junit '**/target/surefire-reports/TEST-*.xml'
   }
   stage('Deploy') {
      //def heroku_auth = 29c64bd7-9da5-4f06-8cff-51cd7f6ae6b4
      sh 'docker login --username=_ --password=29c64bd7-9da5-4f06-8cff-51cd7f6ae6b4 registry.heroku.com'
      sh 'heroku container:push web --app=rocky-brushlands-25964'
	  sh 'heroku container:release web --app=rocky-brushlands-25964'
   }
}
