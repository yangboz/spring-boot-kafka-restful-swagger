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

## References

https://www.e4developer.com/2018/05/21/getting-started-with-kafka-in-spring-boot/

https://docs.confluent.io/current/installation/docker/docs/installation/single-node-client.html

