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
			}
		}
		stage('Unit Tests'){
			steps{
				echo"-------------> These are Unit Test !! <------------"
			}
		}
		stage('Integration Test'){
			steps{
				echo"--------> These are Integration Test !! <----------"
			}
		}
		stage('Static Code Analysis'){
			steps{
				echo"-------> This is Static Code Analysis !! <---------"
			}
		}
		stage('Build'){
			steps{
				echo"--------> This is Build !! <----------"
			}
		}
	}
	post {
		always {
			echo 'This will always run !! '
		}
		success {
			echo 'This will run only if all is successful  !! :)'
		}
		failure {
			echo 'This will run only if failed !! :('
		}
		unstable {
			echo 'This will run only if the run was marked as unstable !!  :S'
		}
		changed {
			echo 'This will run only if the state of the Pipeline has changed'
			echo 'For example, if the Pipeline was previously failing but is now successful'
		}
	}
}