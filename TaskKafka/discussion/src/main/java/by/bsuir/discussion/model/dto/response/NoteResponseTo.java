package by.bsuir.discussion.model.dto.response;

import by.bsuir.discussion.model.entity.NoteStateType;

public record NoteResponseTo(
        Long id,
        Long tweetId,
        String content,
        NoteStateType state
) {
}
