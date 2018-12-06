package info.smartkit.cloud.streaming.services;

import info.smartkit.cloud.streaming.dto.KafkaMessage;

public interface KafkaService {
    void send(KafkaMessage kafkaMessage);
}
