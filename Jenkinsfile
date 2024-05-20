#!groovy

final BUILD_NODE_SELECTOR="build"
final DEPLOY_NODE_SELECTOR="deploy"
final ORIGIN_GIT_URL = "https://github.com/bsantanna/learnmate.git"
final SONAR_CREDENTIALS_ID = "sonar_credentials"
final SONAR_URL = "https://secure.btech.software/sonarqube"

catchError {

  stage("Build Project") {

    properties(
      [
        parameters([
          string(name: 'TAG')
        ])
      ]
    )

    node(BUILD_NODE_SELECTOR) {

      env.SONAR_URL = SONAR_URL

      deleteDir()

      git url: ORIGIN_GIT_URL, tag: params.TAG, branch:"main"

      withCredentials([string(credentialsId: SONAR_CREDENTIALS_ID, variable: "SONAR_CREDENTIALS")]) {
        env.SONAR_CREDENTIALS = SONAR_CREDENTIALS
      }

      withMaven(maven: 'default', options: [artifactsPublisher(disabled: true)]) {
        withPythonEnv('python3') {
          sh 'pip install --upgrade pip'
          sh 'pip install -r requirements.txt'
          sh 'mvn clean verify sonar:sonar -Pcoverage -Dsonar.host.url=${SONAR_URL} -Dsonar.token=${SONAR_CREDENTIALS}'
          sh 'mvn package -DskipTests'
        }
      }

    }
  }


}
