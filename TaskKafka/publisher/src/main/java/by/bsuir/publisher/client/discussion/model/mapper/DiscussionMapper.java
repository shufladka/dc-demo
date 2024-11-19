package by.bsuir.publisher.client.discussion.model.mapper;

import by.bsuir.publisher.client.discussion.model.request.NoteRequestTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiscussionMapper {

    @Mapping(target = "state", expression = "java(by.bsuir.publisher.client.discussion.model.StateType.PENDING)")
    NoteRequestTo toRequestTo(by.bsuir.publisher.model.dto.request.NoteRequestTo noteRequestTo, String country);
}
