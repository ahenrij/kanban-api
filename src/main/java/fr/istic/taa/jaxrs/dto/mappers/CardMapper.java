package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.dto.CardDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CardMapper {

    CardMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CardMapper.class);

    void updateAttrs(CardDto dto, @MappingTarget Card entity);

    CardDto map(Card entity);
}
