# A simple servlet application with Piranha Cloud in AWS Lambda

## Build

```
mvn clean package
```

## Prepare AWS environment

Install AWS-CLI and CDK, authenticate using AWS CLI

Then run

* `cd piranha-function-infra`
* `cdk bootstrap` (installs CDK services to the AWS account, only once for an AWS account)
* `cdk deploy`

To update a function, run one of:

* `cdk deploy --hotswap`
* `./manage.sh update`

Both options should reset warm lambda's even if the code doesn't change.

## Run

* `cd piranha-function-infra`
* `./manage.sh invoke`

## Snapstart

To run with Snapstart optimization, we need to run a specific version/alias of the function:

* `cd piranha-function-infra`
* `AWS_LAMBDA_VERSION=MyVersion ./manage.sh invoke`
