package by.bsuir.discussion.util;

import by.bsuir.discussion.model.entity.Note;
import by.bsuir.discussion.model.entity.NoteStateType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class NoteFilterUtil {

    private List<String> forbiddenWordList;

    public NoteFilterUtil() {
        forbiddenWordList = Arrays.asList(
                "forbidden-word-1",
                "forbidden-word-2",
                "forbidden-word-3"
        );
    }

    public Note filterNote(Note note) {
        NoteStateType state = forbiddenWordList.stream()
                .anyMatch(forbiddenWord -> note.getContent().matches(".*\\b" + forbiddenWord + "\\b.*"))
                ? NoteStateType.DECLINED
                : NoteStateType.APPROVED;

        note.setState(state);
        return note;
    }
}
