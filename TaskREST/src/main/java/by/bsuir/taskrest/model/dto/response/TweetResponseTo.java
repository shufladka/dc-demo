package by.bsuir.taskrest.model.dto.response;

import java.time.LocalDateTime;

public record TweetResponseTo(
        Long id,
        Long authorId,
        String title,
        String content,
        LocalDateTime created,
        LocalDateTime modified
) {
}
