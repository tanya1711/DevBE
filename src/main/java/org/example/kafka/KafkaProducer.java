package org.example.kafka;


import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    public void sendMessage(String topicName,String key,String msg){
        kafkaTemplate.send(topicName,key,msg);
        System.out.println("message sent!! "+msg);
    }

}
