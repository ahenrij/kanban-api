package fr.istic.sir.dto.mappers;

import fr.istic.sir.dto.CardDto;
import fr.istic.sir.model.Card;
import fr.istic.sir.utils.Utils;
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
