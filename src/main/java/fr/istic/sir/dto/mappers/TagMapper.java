package fr.istic.sir.dto.mappers;

import fr.istic.sir.dto.TagDto;
import fr.istic.sir.model.Tag;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TagMapper.class);

    void updateAttrs(TagDto dto, @MappingTarget Tag entity);

    TagDto map(Tag entity);
}
