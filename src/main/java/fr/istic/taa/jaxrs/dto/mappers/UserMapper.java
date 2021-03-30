package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

    void updateAttrs(UserDto dto, @MappingTarget User entity);

    UserDto map(User user);
}
