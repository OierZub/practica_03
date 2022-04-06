# Pasos para realizar la práctica

## 1.- Instalación de SonarQube en local

1.- Descargamos SonarQube Community Edition desde la siguiente dirección en nuestro directorio de trabajo
	
	https://www.sonarqube.org/success-download-community-edition/

	Versión utilizada: sonarqube-9.3.0.51899

2.- Descomprimimos el fichero descargado y procedemos a arrancar SonarQube

	Double click sobre el siguienet fichero:  C:\temp_fp\sonarqube-9.3.0.51899\bin\windows-x86-64\StartSonar.bat
	
	Se abrirá una consola de comandas y esperaremos hasta que SonarQube esté arrancado.
	
3.- Acceder a SonarQube 

	Abrir la siguiente URL desde el navegador web http://localhost:9000/
	Introducir credenciales: admin / admin
	Cambiar la constraseña de admin por: adminfp

## 2.- Instalación de SonarQube scanner en Jenkins

	Accedemos la página de instalación de plugins en Jenkins y  buscamos por sonarqube:
	
		http://localhost:8080/pluginManager/available
	
	Seleccionamos el plugin SonarQube Scanner y pulsamos el botón "Download now and install after restart"
	
	En la siguiente pantalla seleccionamos la opción que nos permite reiniciar Jenkins una vez instalado el plugin de SonarQube Scanner.

	Una vez reiniciado Jenkins accedemos la página de administración (http://localhost:8080/manage) y seleccionamos "Configurar el sistema" (http://localhost:8080/configure)
	
	Buscamos la sección "SonarQube servers" y añadimos nuestro servidor local
	
		http://localhost:9000


## 3.- Generación de token de autenticación desde SonarQube

	3.1 Creamos un nuevo token que utilizaremos para conectarnos a SonarQube desde Jenkins

	http://localhost:9000/account/security/
	
	3.2 Copiamos el token generado para poder pasarlo como parámetro en el siguiente paso.
	
	
## 4.- Actualización de Jenkinsfile para añadir stage de SonarQube


	stage('Calidad de código (SAST)') 
	{    
		echo 'Auditando código de WebApp'    
    
        withSonarQubeEnv {
            bat 'mvn clean package sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<token>'
		}
    }


## 5.- Configuración de la instalación de maven y del jdk en la página de configuración de herramientas de Jenkins

	http://localhost:8080/configureTools/
	
	JDK:
		Nombre: jdk11
		
		JAVA_HOME: C:\dev\java\jdk-11
	
	Maven:
		Nombre: maven-3
		
		JAVA_HOME: C:\dev\apache-maven-3.8.1
	
	

## 6.- Actualización e Jenkinsfile para añadir la instalación de maven y del jdk al path

	6.1 Llamamos a la función "setMavenThreeAndJavaEleven()" justo antes de la definición del primer stage:
	
		--
		node {

			setMavenThreeAndJavaEleven()

			stage ('Build App')
			
		--
	
	6.2 Añadimos las funciones de definición de variables de entorno para maven y jdk al final del fichero Jenkinsfile:
	
	
		--
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
		--

## 7. - Ejecución de Sonarqube en Jenkins

	7.1 Creamos nueva tarea Multibranch y la configuramos para que apunte a nuestro repositorio de Github tal y como se vió en la práctica 02
	
	7.2 Ejecutamos nuevo build donde podemos ver que se ejecuta el estage de Sonarqube
	
	7.3 Una vez terminado, accedemos a SonarQube para ver los resultados obtenidos.

## 8. - Uso de Quality Gates

	8.1 Creamos quality gate que falle cuando se introducen nuevos blocker issues (Por ejemplo con el uso de System.out.println("..."))