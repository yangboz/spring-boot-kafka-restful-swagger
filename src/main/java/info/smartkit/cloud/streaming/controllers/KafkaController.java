package info.smartkit.cloud.streaming.controllers;

import info.smartkit.cloud.streaming.dto.JsonObject;
import info.smartkit.cloud.streaming.dto.JsonString;
import info.smartkit.cloud.streaming.dto.KafkaMessage;
import io.swagger.annotations.ApiOperation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
// @see: https://www.e4developer.com/2018/05/21/getting-started-with-kafka-in-spring-boot/
@RequestMapping(value = "v1/cloud/stream/kafka")
public class KafkaController {
    private static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(KafkaController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    @ApiOperation(value = "Response a string describing Kafka info.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    JsonString info() {
        return new JsonString("v0.0.1");
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the access_token related user profile is successfully received.")
    public void produce(@RequestBody @Valid KafkaMessage kafkaMessage) {
        //
        kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage.getPayload());
        LOG.info("Message: "+kafkaMessage.getPayload()+" sent to topic: "+kafkaMessage.getTopic());
    }
    @KafkaListener(topics = "topic1")
    public void receiveTopic1(ConsumerRecord<?, ?> consumerRecord) {
        System.out.println("Receiver on topic1: "+consumerRecord.toString());
    }
}
