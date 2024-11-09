package by.bsuir.taskjpa.model.mapper;

import by.bsuir.taskjpa.model.dto.request.StickerRequestTo;
import by.bsuir.taskjpa.model.dto.response.StickerResponseTo;
import by.bsuir.taskjpa.model.entity.Sticker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StickerMapper {
    Sticker toEntity(StickerRequestTo request);
    StickerResponseTo toResponseTo(Sticker entity);
    List<StickerResponseTo> toResponseTo(List<Sticker> entities);
}
