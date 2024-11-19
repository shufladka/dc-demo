package by.bsuir.publisher.model.dto.response;

import by.bsuir.publisher.client.discussion.model.entity.StateType;

public record NoteResponseTo(
        Long id,
        Long tweetId,
        String content,
        StateType state
) {
}
