package taskmanagementsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import taskmanagementsystem.dto.auth.JwtResponse;
import taskmanagementsystem.model.user.User;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JwtMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    JwtResponse toDto(User user);


}