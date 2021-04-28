package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Team;
import fr.istic.taa.jaxrs.dto.TeamDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-20T16:39:57+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
public class TeamMapperImpl implements TeamMapper {

    @Override
    public void updateAttrs(TeamDto dto, Team entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setTitle( dto.getTitle() );
    }

    @Override
    public TeamDto map(Team entity) {
        if ( entity == null ) {
            return null;
        }

        TeamDto teamDto = new TeamDto();

        teamDto.setId( entity.getId() );
        teamDto.setTitle( entity.getTitle() );

        return teamDto;
    }
}
