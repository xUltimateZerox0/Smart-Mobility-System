#!/bin/bash
#mvn clean verify sonar:sonar \
# -Dsonar.projectKey=<your-project-key> \
# -Dsonar.host.url=http://localhost:9000 \
# -Dsonar.login=<your-sonar-token>

###########
###ALTERNATIVE: If you have a .env file with the necessary variables, you can source it and run the command without hardcoding the values.
###########

source .env
mvn clean verify sonar:sonar -Dsonar.projectKey=smart-mobility-system -Dsonar.projectName='Smart Mobility System'
