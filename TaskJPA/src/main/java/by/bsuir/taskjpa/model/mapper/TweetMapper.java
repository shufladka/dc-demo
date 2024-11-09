package by.bsuir.taskjpa.model.mapper;

import by.bsuir.taskjpa.model.dto.request.TweetRequestTo;
import by.bsuir.taskjpa.model.dto.response.TweetResponseTo;
import by.bsuir.taskjpa.model.entity.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TweetMapper {

    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "modified", expression = "java(java.time.LocalDateTime.now())")
    Tweet toEntity(TweetRequestTo request);

    TweetResponseTo toResponseTo(Tweet entity);
    List<TweetResponseTo> toResponseTo(List<Tweet> entities);
}
