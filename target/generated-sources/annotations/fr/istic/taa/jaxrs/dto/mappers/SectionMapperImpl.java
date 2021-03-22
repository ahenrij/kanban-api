package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.dto.SectionDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-22T00:57:12+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
public class SectionMapperImpl implements SectionMapper {

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
