package by.bsuir.publisher.client.discussion.kafka.service;

import by.bsuir.publisher.client.discussion.kafka.dto.KafkaRequestTo;
import by.bsuir.publisher.client.discussion.kafka.dto.KafkaResponseTo;
import by.bsuir.publisher.client.discussion.kafka.exception.KafkaResponseException;
import by.bsuir.publisher.client.discussion.kafka.exception.KafkaTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

    @Value("${topic.tweet-note.request}")
    private String requestTopic;

    private final ReplyingKafkaTemplate<String, KafkaRequestTo, KafkaResponseTo> replyingKafkaTemplate;

    public KafkaResponseTo sendAndReceive(KafkaRequestTo topicMessage) {
        log.info("Sending request message: {}", topicMessage);

        String recordKey = isMessageIssued(topicMessage)
                ? topicMessage.discussionRequestTo().tweetId().toString()
                : null;

        ProducerRecord<String, KafkaRequestTo> record =
                new ProducerRecord<>(requestTopic, recordKey, topicMessage);
        RequestReplyFuture<String, KafkaRequestTo, KafkaResponseTo> replyFuture =
                replyingKafkaTemplate.sendAndReceive(record);

        try {
            ConsumerRecord<String, KafkaResponseTo> consumerRecord = replyFuture.get(3, TimeUnit.SECONDS);
            log.info("Received response message: {}", consumerRecord.value());
            return consumerRecord.value();
        }
        catch (TimeoutException e) {
            throw new KafkaTimeoutException();
        }
        catch (ExecutionException | InterruptedException e) {
            throw new KafkaResponseException(e.getMessage());
        }
    }

    private boolean isMessageIssued(KafkaRequestTo topicMessage) {
        return (topicMessage.discussionRequestTo() != null) && (topicMessage.discussionRequestTo().tweetId() != null);
    }
}
