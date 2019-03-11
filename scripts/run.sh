#!/bin/bash

function run(){
    mkdir -p ~/.aws
    echo "[default]
    aws_access_key_id=AKIAIOSFODNN7EXAMPLE
    aws_secret_access_key=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" > ~/.aws/credentials

    docker run --name dynamodbContainer -p 8000:8000 amazon/dynamodb-local &

    ./gradlew clean bootRun
}

function finish {
    docker kill dynamodbContainer
    docker rm dynamodbContainer
}

trap finish EXIT
#trap finish SIGINT

run