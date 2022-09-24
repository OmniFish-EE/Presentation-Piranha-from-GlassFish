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


## Run

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.PiranhaApp
```

Then access the app at http://localhost:8080


## Run with timing

```
STARTUP_PHRASE='Application started' bash ../measure-startup-time.sh \
  piranha http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.PiranhaApp
```

## Run faster, without HTTP and logging

### Run

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.PiranhaMinimalApp
```

Prints a response immediately.

### Run with timing

```
STARTUP_PHRASE='Application started' bash ../measure-startup-time.sh \
  piranha-minimal - java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.piranha.PiranhaMinimalApp
```