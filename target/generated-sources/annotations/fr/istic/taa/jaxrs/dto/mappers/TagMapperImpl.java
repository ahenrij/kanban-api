package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Tag;
import fr.istic.taa.jaxrs.dto.TagDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-20T16:39:57+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
public class TagMapperImpl implements TagMapper {

    @Override
    public void updateAttrs(TagDto dto, Tag entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setColor( dto.getColor() );
    }

    @Override
    public TagDto map(Tag entity) {
        if ( entity == null ) {
            return null;
        }

        TagDto tagDto = new TagDto();

        tagDto.setId( entity.getId() );
        tagDto.setName( entity.getName() );
        tagDto.setColor( entity.getColor() );

        return tagDto;
    }
}
