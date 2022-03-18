package fr.istic.sir.dto.mappers;

import fr.istic.sir.dto.TeamDto;
import fr.istic.sir.model.Team;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface TeamMapper {

    TeamMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TeamMapper.class);

    void updateAttrs(TeamDto dto, @MappingTarget Team entity);

    TeamDto map(Team entity);
}
