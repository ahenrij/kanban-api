package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Tag;
import fr.istic.taa.jaxrs.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TagMapper.class);

    void updateAttrs(TagDto dto, @MappingTarget Tag entity);

    TagDto map(Tag entity);
}
