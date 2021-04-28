package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.dto.SectionDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-20T16:39:57+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
public class SectionMapperImpl implements SectionMapper {

    @Override
    public void updateAttrs(SectionDto dto, Section entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setTitle( dto.getTitle() );
        entity.setPosition( dto.getPosition() );
    }

    @Override
    public SectionDto map(Section entity) {
        if ( entity == null ) {
            return null;
        }

        SectionDto sectionDto = new SectionDto();

        sectionDto.setId( entity.getId() );
        sectionDto.setTitle( entity.getTitle() );
        sectionDto.setPosition( entity.getPosition() );

        return sectionDto;
    }

    @Override
    public Section map(SectionDto sectionDto) {
        if ( sectionDto == null ) {
            return null;
        }

        Section section = new Section();

        section.setId( sectionDto.getId() );
        section.setTitle( sectionDto.getTitle() );
        section.setPosition( sectionDto.getPosition() );

        return section;
    }
}
