#!groovy

final BUILD_NODE_SELECTOR="build"
final DEPLOY_NODE_SELECTOR="deploy"
final ORIGIN_GIT_URL = "https://github.com/bsantanna/learnmate.git"
final REGISTRY_ENDPOINT= "secure.btech.software"
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

      env.IMAGE_TAG = params.TAG
      env.REGISTRY_ENDPOINT = REGISTRY_ENDPOINT
      env.SONAR_URL = SONAR_URL

      deleteDir()

      checkout(
        [
          $class: 'GitSCM',
          branches: [[name: "refs/tags/${params.TAG}"]],
          doGenerateSubmoduleConfigurations: false,
          extensions: [],
          submoduleCfg: [],
          userRemoteConfigs: [[url: ORIGIN_GIT_URL]]
        ]
      )

      withCredentials([string(credentialsId: SONAR_CREDENTIALS_ID, variable: "SONAR_CREDENTIALS")]) {
        env.SONAR_CREDENTIALS = SONAR_CREDENTIALS
      }

      withMaven(maven: 'default', options: [artifactsPublisher(disabled: true)]) {
        withPythonEnv('python3') {
          sh 'pip install --upgrade pip --quiet'
          sh 'pip install -r requirements.txt --quiet'
          sh 'mvn clean verify sonar:sonar -Pcoverage -Dsonar.host.url=${SONAR_URL} -Dsonar.token=${SONAR_CREDENTIALS}'
          sh 'mvn package -DskipTests'
        }
      }

      sh 'docker tag learnmate-api-web:latest ${REGISTRY_ENDPOINT}/learnmate-api-web:${IMAGE_TAG}'
      sh 'docker tag learnmate-api-web:latest ${REGISTRY_ENDPOINT}/learnmate-api-web:latest'
      sh 'docker push ${REGISTRY_ENDPOINT}/learnmate-api-web:${IMAGE_TAG}'
      sh 'docker push ${REGISTRY_ENDPOINT}/learnmate-api-web:latest'

    }
  }


  stage("Deploy Project") {
    node(DEPLOY_NODE_SELECTOR) {

      deleteDir()

      checkout(
        [
          $class: 'GitSCM',
          branches: [[name: "refs/tags/${params.TAG}"]],
          doGenerateSubmoduleConfigurations: false,
          extensions: [],
          submoduleCfg: [],
          userRemoteConfigs: [[url: ORIGIN_GIT_URL]]
        ]
      )

      dir('azure/learnmate-sweden-central/aks-learnmate-v1/charts/api-web') {
        sh "kubectl config set-context --current --namespace='learnmate'"
        sh "helm upgrade --install learnmate-api-web ."
      }

    }
  }

}
