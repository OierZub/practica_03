node {
	// Para que lea las variables de entorno
	setMavenThreeAndJavaEleven()

	stage ('Build App')
	{
		echo 'Running Build App'
		
		checkout scm
		
		bat 'mvn clean package'
	}

	stage ('Run unit test')
	{
		echo 'Running Unit Tests'

		bat 'mvn clean test -Dtest=HolaControllerTest'
					
		step([$class: 'JUnitResultArchiver', testResults: "**/surefire-reports/*.xml"]) 
			
	}

	stage ('Run Integration test')
	{
		echo 'Running Integration Tests'

		bat 'mvn clean verify'
									
		step([$class: 'JUnitResultArchiver', testResults: '**/failsafe-reports/TEST-*.xml'])

	}
	
	stage('Calidad de código (SAST)') 
	{    
		echo 'Auditando código de WebApp'    

		withSonarQubeEnv {
			bat 'mvn clean package sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=17227cfcf3fd516e1839c849e3c23c401b14747b'
		}

	}
}


/**
 * Set maven 3 and Java 11 in path
 */
def setMavenThreeAndJavaEleven() {
	env.M2_HOME="${tool 'maven-3'}"
	env.JAVA_HOME="${tool 'jdk11'}"
	env.PATH="${env.JAVA_HOME}/bin;${env.PATH}"
	env.PATH="${env.M2_HOME}/bin;${env.PATH}"
	echo '*** env.PATH: ' + env.PATH
}
