package fr.istic.sir.dto.mappers;

import fr.istic.sir.dto.BoardDto;
import fr.istic.sir.model.Board;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface BoardMapper {

    BoardMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BoardMapper.class);

    void updateAttrs(BoardDto dto, @MappingTarget Board entity);

    BoardDto map(Board entity);
}
