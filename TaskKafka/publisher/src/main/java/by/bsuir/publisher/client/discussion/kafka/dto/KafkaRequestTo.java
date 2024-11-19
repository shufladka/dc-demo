package by.bsuir.publisher.client.discussion.kafka.dto;

import by.bsuir.publisher.client.discussion.model.request.NoteRequestTo;
import lombok.Builder;

@Builder
public record KafkaRequestTo(
        OperationType operation,
        NoteRequestTo noteRequestTo,
        Integer pageNumber,
        Integer pageSize
) {
}
