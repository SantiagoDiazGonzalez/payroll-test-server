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
     withCredentials([usernamePassword(credentialsId: 'herokuCredentials', passwordVariable: 'password', usernameVariable: 'user')]) {
	   sh 'docker login --username=_ --password=${password} registry.heroku.com'
	   sh 'docker tag santiagodiazgonzalez/payroll-santiago registry.heroku.com/rocky-brushlands-25964/web'
	   sh 'docker push registry.heroku.com/rocky-brushlands-25964/web'
	   // sh 'heroku container:release web --app=rocky-brushlands-25964'
	   sh 'curl -n -X PATCH https://api.heroku.com/apps//formation \
        -d '{
			"updates": [
			{
			"type": "web",
			"docker_image": "0350fc97ea0d301c9292b5cc0a57522e4812d8c72bbb456e673af5d63ae3e00a"
			},
			]
		}' \
		-H "Content-Type: application/json" \
		-H "Accept: application/vnd.heroku+json; version=3.docker-releases"'
	 }
   }
}
