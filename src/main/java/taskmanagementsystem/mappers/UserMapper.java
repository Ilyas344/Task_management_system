package taskmanagementsystem.mappers;

import org.mapstruct.*;
import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;
import taskmanagementsystem.model.user.User;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    User updateUserFromDto(UserRequest userDto, @MappingTarget User existingUser);



    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    User userDtoMapper(UserRequest user);


    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    UserResponse userMapper(User user);

}
