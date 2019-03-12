function finish {
    docker kill dynamodbContainer
    docker rm dynamodbContainer
}
echo "Killing and removing docker images..."
finish > /dev/null
echo "Done!"