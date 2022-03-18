package fr.istic.sir.dto.mappers;

import fr.istic.sir.dto.UserDto;
import fr.istic.sir.dto.UserRegisterDto;
import fr.istic.sir.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

    void updateAttrs(UserDto dto, @MappingTarget User entity);

    void updateAttrs(UserRegisterDto dto, @MappingTarget User entity);

    UserDto map(User user);
}
