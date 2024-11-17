package by.bsuir.discussion.kafka.service;

import by.bsuir.discussion.kafka.dto.KafkaRequestTo;
import by.bsuir.discussion.kafka.dto.KafkaResponseTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final KafkaHandlerService handler;

    @KafkaListener(id = "discussion", topics = "${topic.tweet-note.request}", groupId = "discussion")
    @SendTo
    public KafkaResponseTo listen(ConsumerRecord<String, KafkaRequestTo> message) {
        log.info("Received message: {}", message.value());
        KafkaResponseTo response = handler.handleMessage(message.value());
        log.info("Sending response: {}", response);
        return response;
    }
}
