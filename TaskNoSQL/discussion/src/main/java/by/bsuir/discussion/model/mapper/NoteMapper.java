package by.bsuir.discussion.model.mapper;

import by.bsuir.discussion.model.dto.request.NoteRequestTo;
import by.bsuir.discussion.model.dto.response.NoteResponseTo;
import by.bsuir.discussion.model.entity.Note;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteMapper {

    @Mapping(target = "key", expression = "java(new Note.Key(request.country(), request.id(), request.tweetId()))")
    Note toEntity(NoteRequestTo request);

    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "key.tweetId", source = "tweetId")
    @Mapping(target = "key.country", source = "country")
    Note updateEntity(@MappingTarget Note entityToUpdate, NoteRequestTo updateRequest);

    @Mapping(target = "id", source = "entity.key.id")
    @Mapping(target = "tweetId", source = "entity.key.tweetId")
    NoteResponseTo toResponseTo(Note entity);
}
