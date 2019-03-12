#!/bin/bash

dbcontainername=dynamodbContainer

function run_app(){
    $(dirname $(dirname $(readlink -f "$0")))/gradlew clean bootRun
}

function run_docker_local_database(){
    ([[ $(docker ps -f "name=$dbcontainername" --format '{{.Names}}') == $dbcontainername ]]  ||
    docker run --name $dbcontainername -p 8000:8000 amazon/dynamodb-local) &
}

function create_local_credentials(){
    mkdir -p ~/.aws
    echo "[default]
    aws_access_key_id=AKIAIOSFODNN7EXAMPLE
    aws_secret_access_key=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" > ~/.aws/credentials
}
function run(){
    create_local_credentials
    run_docker_local_database
    run_app
}

run