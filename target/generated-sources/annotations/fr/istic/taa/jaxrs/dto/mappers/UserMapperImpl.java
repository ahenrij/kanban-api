package fr.istic.taa.jaxrs.dto.mappers;

import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.UserDto;
import fr.istic.taa.jaxrs.dto.UserRegisterDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-30T20:51:06+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public void updateAttrs(UserDto dto, User entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setFirstName( dto.getFirstName() );
        entity.setLastName( dto.getLastName() );
        entity.setEmail( dto.getEmail() );
    }

    @Override
    public void updateAttrs(UserRegisterDto dto, User entity) {
        if ( dto == null ) {
            return;
        }

        entity.setFirstName( dto.getFirstName() );
        entity.setLastName( dto.getLastName() );
        entity.setEmail( dto.getEmail() );
        entity.setPassword( dto.getPassword() );
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
