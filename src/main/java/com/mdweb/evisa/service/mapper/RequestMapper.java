package com.mdweb.evisa.service.mapper;

import com.mdweb.evisa.domain.Request;
import com.mdweb.evisa.domain.User;
import com.mdweb.evisa.service.dto.RequestDTO;
import com.mdweb.evisa.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Request} and its DTO {@link RequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    RequestDTO toDto(Request s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
