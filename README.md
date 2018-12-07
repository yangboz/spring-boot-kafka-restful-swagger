# spring-boot-kafka-restful-swagger
A SpringBoot RESTful, SwaggerUI API documentation example for Kafka.

## Kafka in docker

https://docs.confluent.io/current/installation/docker/docs/installation/single-node-client.html

### Create a topic
```
docker exec docker-name-kafka usr/bin/kafka-topics --create --topic input --partitions input-0 
--replication-factor 1 --if-not-exists --zookeeper docker-name-zookeeper:2181
```
### Describe created topic
```
docker exec docker-name-kafka usr/bin/kafka-topics --describe --topic input --zookeeper docker-name-zookeeper
```
### Produce message to topic
```
docker exec  kafka kafka-console-producer --request-required-acks 1 \
--broker-list docker-name-kafka:9092 --topic input && echo 'messages.'
```
### Consume topic's message

```
docker exec  docker-name-kafka usr/bin/kafka-console-consumer --bootstrap-server docker-name-kafka:9092 
--topic test --from-beginning --max-messages 42
```

## Gitlab events example

https://raw.githubusercontent.com/yangboz/spring-boot-kafka-restful-swagger/master/src/main/resources/GitlabEvents.json

### TODO

#1.Customize define Events provider, Incentive provider in Dashboard.

#2.Spring Data flow/Stream version

## Demo

Docker run:

```
docker run -p 8096:8080 smartkit/restful-gitlab-kafka-steem:0.1
```

Swagger UI for RESTful APIs:

```
http://localhost:8096/swagger-ui.html
```
## References

https://www.e4developer.com/2018/05/21/getting-started-with-kafka-in-spring-boot/

https://github.com/simplesteph/kafka-stack-docker-compose

https://docs.confluent.io/current/installation/docker/docs/installation/single-node-client.html

https://www.e4developer.com/2018/02/18/getting-started-with-spring-cloud-data-flow/

https://spring.io/blog/2018/04/19/kafka-streams-and-spring-cloud-stream

https://github.com/gmessner/gitlab4j-api#eventsapi

https://github.com/marvin-we/steem-java-api-wrapper

https://steemit.com/utopian-io/@dez1337/steemj-v0-4-3-is-available-now-use-the-steem-api-in-your-java-project

