#!/bin/bash

FUNCTION_NAME=PiranhaFunction
HANDLER=ee.omnifish.piranhafromgf.piranha.PiranhaFunction::handleRequest
ZIP_FILE=fileb://../piranha-function/target/piranha-function-package.jar
#AWS_REGION=eu-central-1

function cmd_invoke() {
  echo Invoking function

  inputFormat=""
  if [ $(aws --version | awk '{print substr($1,9)}' | cut -c1-1) -ge 2 ]; then inputFormat="--cli-binary-format raw-in-base64-out"; fi

  set -x

  local REGION=""
  if [ -n "$AWS_REGION" ]
    then
      REGION="--region $AWS_REGION"
  fi

  aws lambda invoke response.txt \
    ${inputFormat} \
    --function-name ${FUNCTION_NAME} \
    $REGION \
    --payload file://payload.json \
    --log-type Tail \
    --query 'LogResult' \
    --output text |  base64 --decode
  { set +x; } 2>/dev/null
  cat response.txt && rm -f response.txt
}

function cmd_update() {
  local REGION=""
  if [ -n "$AWS_REGION" ]
    then
      REGION="--region $AWS_REGION"
  fi

  echo Updating function
  set -x
  aws lambda update-function-code \
    $REGION \
    --function-name ${FUNCTION_NAME} \
    --zip-file ${ZIP_FILE}
}

function usage() {
  [ "_$1" == "_" ] && echo -e "\nUsage (JVM): \n$0 [create|delete|invoke]\ne.g.: $0 invoke"
  [ "_$1" == "_" ] && echo -e "\nUsage (Native): \n$0 native [create|delete|invoke]\ne.g.: $0 native invoke"

  [ "_" == "_`which aws 2>/dev/null`" ] && echo -e "\naws CLI not installed. Please see https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html"
  [ ! -e $HOME/.aws/credentials ] && [ "_$AWS_ACCESS_KEY_ID" == "_" ] && echo -e "\naws configure not setup.  Please execute: aws configure"
  [ "_$LAMBDA_ROLE_ARN" == "_" ] && echo -e "\nEnvironment variable must be set: LAMBDA_ROLE_ARN\ne.g.: export LAMBDA_ROLE_ARN=arn:aws:iam::123456789012:role/my-example-role"
}

if [ "_$1" == "_" ] || [ "$1" == "help" ]
 then
  usage
fi

while [ "$1" ]
do
  eval cmd_${1}
  { set +x; } 2>/dev/null
  shift
done

