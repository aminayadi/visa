package com.mdweb.evisa.service.mapper;

import com.mdweb.evisa.domain.Approval;
import com.mdweb.evisa.domain.Request;
import com.mdweb.evisa.domain.User;
import com.mdweb.evisa.service.dto.ApprovalDTO;
import com.mdweb.evisa.service.dto.RequestDTO;
import com.mdweb.evisa.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Approval} and its DTO {@link ApprovalDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalMapper extends EntityMapper<ApprovalDTO, Approval> {
    @Mapping(target = "requests", source = "requests", qualifiedByName = "requestIdSet")
    @Mapping(target = "users", source = "users", qualifiedByName = "userLoginSet")
    ApprovalDTO toDto(Approval s);

    @Mapping(target = "removeRequest", ignore = true)
    @Mapping(target = "removeUser", ignore = true)
    Approval toEntity(ApprovalDTO approvalDTO);

    @Named("requestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDTO toDtoRequestId(Request request);

    @Named("requestIdSet")
    default Set<RequestDTO> toDtoRequestIdSet(Set<Request> request) {
        return request.stream().map(this::toDtoRequestId).collect(Collectors.toSet());
    }

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("userLoginSet")
    default Set<UserDTO> toDtoUserLoginSet(Set<User> user) {
        return user.stream().map(this::toDtoUserLogin).collect(Collectors.toSet());
    }
}
