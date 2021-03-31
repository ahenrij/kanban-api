package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.dto.BoardDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface BoardMapper {

    BoardMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BoardMapper.class);

    void updateAttrs(BoardDto dto, @MappingTarget Board entity);

    BoardDto map(Board entity);
}
