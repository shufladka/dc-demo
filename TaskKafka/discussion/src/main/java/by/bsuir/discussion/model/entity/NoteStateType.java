package by.bsuir.discussion.model.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum NoteStateType {
    PENDING("pending"),
    APPROVED("approved"),
    DECLINED("declined");

    private final String state;

    NoteStateType(String state) {
        this.state = state;
    }
}
