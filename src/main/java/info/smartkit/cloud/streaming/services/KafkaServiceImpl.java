package info.smartkit.cloud.streaming.services;

import info.smartkit.cloud.streaming.dto.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService{

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(KafkaMessage kafkaMessage) {
        kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage.getPayload());
    }
}
