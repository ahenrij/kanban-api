package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.dto.CardDto;
import fr.istic.taa.jaxrs.dto.SectionDto;
import java.sql.Date;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-23T01:00:17+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
public class MappersImpl implements Mappers {

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

    @Override
    public Card map(CardDto cardDto) {
        if ( cardDto == null ) {
            return null;
        }

        Card card = new Card();

        if ( cardDto.getDeadline() != null ) {
            card.setDeadline( new Date( map( cardDto.getDeadline() ).getTime() ) );
        }
        if ( cardDto.getId() != null ) {
            card.setId( cardDto.getId() );
        }
        card.setLabel( cardDto.getLabel() );
        card.setDuration( cardDto.getDuration() );
        card.setPlace( cardDto.getPlace() );
        card.setUrl( cardDto.getUrl() );
        card.setDescription( cardDto.getDescription() );

        return card;
    }
}
