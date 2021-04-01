package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.dto.CardDto;
import fr.istic.taa.jaxrs.utils.Utils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Date;

@Mapper
public interface CardMapper {

    CardMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CardMapper.class);

    void updateAttrs(CardDto dto, @MappingTarget Card entity);

    CardDto map(Card entity);

    default Date map(String value) {
        return Utils.getDateFromString(value);
    }

    @Mapping(target = "deadline", dateFormat = Utils.DATE_FORMAT)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "assignees", ignore = true)
    Card map(CardDto cardDto);
}
