package fr.istic.sir.dto.mappers;

import fr.istic.sir.dto.SectionDto;
import fr.istic.sir.model.Section;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface SectionMapper {

    SectionMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(SectionMapper.class);

    void updateAttrs(SectionDto dto, @MappingTarget Section entity);

    SectionDto map(Section entity);

    @Mapping(target = "board", ignore = true)
    @Mapping(target = "cards", ignore = true)
    Section map(SectionDto sectionDto);
}
