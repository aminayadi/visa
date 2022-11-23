package com.mdweb.evisa.service.mapper;

import com.mdweb.evisa.domain.Person;
import com.mdweb.evisa.domain.Request;
import com.mdweb.evisa.service.dto.PersonDTO;
import com.mdweb.evisa.service.dto.RequestDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "requests", source = "requests", qualifiedByName = "requestIdSet")
    PersonDTO toDto(Person s);

    @Mapping(target = "removeRequest", ignore = true)
    Person toEntity(PersonDTO personDTO);

    @Named("requestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDTO toDtoRequestId(Request request);

    @Named("requestIdSet")
    default Set<RequestDTO> toDtoRequestIdSet(Set<Request> request) {
        return request.stream().map(this::toDtoRequestId).collect(Collectors.toSet());
    }
}
