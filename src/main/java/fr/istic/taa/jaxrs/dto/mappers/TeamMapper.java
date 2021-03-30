package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.Team;
import fr.istic.taa.jaxrs.dto.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface TeamMapper {

    TeamMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TeamMapper.class);

    void updateAttrs(TeamDto dto, @MappingTarget Team entity);

    TeamDto map(Team entity);
}
