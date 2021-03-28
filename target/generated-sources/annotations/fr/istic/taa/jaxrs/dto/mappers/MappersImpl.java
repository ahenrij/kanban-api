package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.CardDto;
import fr.istic.taa.jaxrs.dto.SectionDto;
import fr.istic.taa.jaxrs.dto.UserDto;
import java.sql.Date;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-27T17:13:19+0100",
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

    @Override
    public UserDto map(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );

        return userDto;
    }
}
