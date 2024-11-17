package by.bsuir.taskrest.model.dto.request;

import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

public record TweetRequestTo(
        Long id,

        @jakarta.validation.constraints.NotNull
        Long userId,

        @NotNull
        @Size(min = 2, max = 64, message = "Title must be between 2 and 64 characters.")
        String title,

        @NotNull
        @Size(min = 4, max = 2048, message = "Content must be between 4 and 2048 characters.")
        String content
) {
}
