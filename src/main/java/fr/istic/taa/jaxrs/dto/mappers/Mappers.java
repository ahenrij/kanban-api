package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.dto.CardDto;
import fr.istic.taa.jaxrs.dto.SectionDto;
import fr.istic.taa.jaxrs.utils.Utils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper
public interface Mappers {

    Mappers INSTANCE = org.mapstruct.factory.Mappers.getMapper(Mappers.class);

    @Mapping(target = "board", ignore = true)
    @Mapping(target = "cards", ignore = true)
    Section map(SectionDto sectionDto);

    @Mapping(target = "deadline", dateFormat = Utils.DATE_FORMAT)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "assignees", ignore = true)
    Card map(CardDto cardDto);

    default Date map(String value) {
        return Utils.getDateFromString(value);
    }
}
