package sympo.service;

import org.mapstruct.Mapper;
import sympo.dto.user.AttendeeDto;
import sympo.entity.AttendeeEntity;

@Mapper(componentModel = "spring")
public interface MapperService {

    AttendeeDto mapUserEntityToDto(AttendeeEntity entity);

}
