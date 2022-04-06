node {

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
}