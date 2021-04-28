package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.dto.BoardDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-28T19:41:14+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Amazon.com Inc.)"
)
public class BoardMapperImpl implements BoardMapper {

    @Override
    public void updateAttrs(BoardDto dto, Board entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setTitle( dto.getTitle() );
        entity.setColor( dto.getColor() );
        entity.setPrivate( dto.isPrivate() );
    }

    @Override
    public BoardDto map(Board entity) {
        if ( entity == null ) {
            return null;
        }

        BoardDto boardDto = new BoardDto();

        boardDto.setId( entity.getId() );
        boardDto.setTitle( entity.getTitle() );
        boardDto.setColor( entity.getColor() );
        boardDto.setPrivate( entity.isPrivate() );

        return boardDto;
    }
}
