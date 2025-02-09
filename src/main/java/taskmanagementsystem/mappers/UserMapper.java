package java.taskmanagementsystem.mappers;

import org.mapstruct.*;

import java.taskmanagementsystem.dto.user.UserRequest;
import java.taskmanagementsystem.dto.user.UserResponse;
import java.taskmanagementsystem.model.user.User;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", ignore = true)
    User updateUserFromDto(UserRequest userDto, @MappingTarget User existingUser);



    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    User userDtoMapper(UserRequest user);


    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    UserResponse userMapper(User user);

}
