package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.dto.CardDto;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.annotation.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-20T16:39:57+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
public class CardMapperImpl implements CardMapper {

    private final DatatypeFactory datatypeFactory;

    public CardMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public void updateAttrs(CardDto dto, Card entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        entity.setLabel( dto.getLabel() );
        if ( dto.getDeadline() != null ) {
            entity.setDeadline( new Date( map( dto.getDeadline() ).getTime() ) );
        }
        else {
            entity.setDeadline( null );
        }
        entity.setDuration( dto.getDuration() );
        entity.setPlace( dto.getPlace() );
        entity.setUrl( dto.getUrl() );
        entity.setDescription( dto.getDescription() );
    }

    @Override
    public CardDto map(Card entity) {
        if ( entity == null ) {
            return null;
        }

        CardDto cardDto = new CardDto();

        cardDto.setId( entity.getId() );
        cardDto.setLabel( entity.getLabel() );
        cardDto.setDeadline( xmlGregorianCalendarToString( dateToXmlGregorianCalendar( entity.getDeadline() ), null ) );
        cardDto.setDuration( entity.getDuration() );
        cardDto.setPlace( entity.getPlace() );
        cardDto.setUrl( entity.getUrl() );
        cardDto.setDescription( entity.getDescription() );

        return cardDto;
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

    private String xmlGregorianCalendarToString( XMLGregorianCalendar xcal, String dateFormat ) {
        if ( xcal == null ) {
            return null;
        }

        if (dateFormat == null ) {
            return xcal.toString();
        }
        else {
            java.util.Date d = xcal.toGregorianCalendar().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );
            return sdf.format( d );
        }
    }

    private XMLGregorianCalendar dateToXmlGregorianCalendar( java.util.Date date ) {
        if ( date == null ) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime( date );
        return datatypeFactory.newXMLGregorianCalendar( c );
    }
}
