pipeline{

	agent {
		label 'Slave_Induccion'
	}
	options{
		buildDiscarder(logRotator(numToKeepStr: '3'))
		disableConcurrentBuilds()
	}
	tools{
		jdk 'JDK8_Centos'
		gradle 'Gradle4.5_Centos'
	}
	stages{
		stage('Checkout'){
			steps{
				echo"-------------> This is Checkout !! <---------------"
				checkout([$class                           : 'GitSCM',
                branches                         : [[name: '*/development']],
                doGenerateSubmoduleConfigurations: false,
                extensions                       : [],
                gitTool                          : 'Git_Centos',
                submoduleCfg                     : [],
                userRemoteConfigs                : [[credentialsId: 'GitHub_rosemberg',
                url          : 'https://github.com/rosemberg-12/Estacionamiento.git']]])
			}
		}
		stage('Compilation'){
			steps{
				echo"-------------> This is a java compilation !! <------------"
				 sh 'gradle --b ./build.gradle compileJava' 
			}
		}
		stage('Tests'){
			steps{
				echo"-------------> These are Unit Test !! <------------"
				sh 'gradle --b ./build.gradle forceTest' 
			}
		}
		stage('Static Code Analysis'){
			steps{
				echo"-------> This is Static Code Analysis !! <---------"
				echo ":Under construction"
				withSonarQubeEnv('Sonar') {
                	sh "${tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
                }
			}
		}
		stage('Build'){
			steps{
				echo"--------> This is Build !! <----------"
				sh 'gradle --b ./build.gradle build -x test'
			}
		}
	}
	post {
		always {
			echo 'Automatic execution finished'
		}
		success {
			echo 'All was fine, congratulations !!  :) '
			mail(to: 'rosemberg.porras@ceiba.com.co',
            subject: " :) Build successfull Pipeline: ${currentBuild.fullDisplayName}",
            body: "All was done, check details ${env.BUILD_URL}")
		}
		failure {
			echo 'Something was bad :( please check and fix it !! :( '
			mail(to: 'rosemberg.porras@ceiba.com.co',
                    subject: " :( Failed Pipeline: ${currentBuild.fullDisplayName}",
                    body: "Something is wrong with ${env.BUILD_URL}")
		}
		unstable {
			echo 'This will run only if the run was marked as unstable !!  :S'
		}
		changed {
			echo 'Something was changed !! '
		}
	}
}