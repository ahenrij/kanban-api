package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.dto.SectionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface SectionMapper {

    SectionMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(SectionMapper.class);

    void updateAttrs(SectionDto dto, @MappingTarget Section entity);

    SectionDto map(Section entity);
}
