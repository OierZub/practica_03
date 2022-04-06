node {

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
		
		publishHTML(target: [
			allowMissing: false, 
			alwaysLinkToLastBuild: true, 
			keepAll: true, 
			reportDir: 'target/site', 
			reportFiles: 'surefire-report.html', 
			reportName: 'Unit Tests report']) 
	}
	
	
    stage('Calidad de c\u00f3digo (SAST)') 
	{    
		echo 'Auditando código de WebApp'    
    
        withSonarQubeEnv {
            bat 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=adminfp compile'                                    
    }

}

/**
 * Set maven 3 and Java 8 in path
 */
def setMavenThreeAndJavaEight() {
	env.M2_HOME="${tool 'maven-3'}"
	env.JAVA_HOME="${tool 'jdk1.8'}"
	env.PATH="${env.JAVA_HOME}/bin;${env.PATH}"
	env.PATH="${env.M2_HOME}/bin;${env.PATH}"
	echo '*** env.PATH: ' + env.PATH
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