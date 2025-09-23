package sympo.dto.user;

import java.util.List;

public record AttendeeListDto(
        Long totalCount,
        Long pageCount,
        List<AttendeeDto> userList
) {}
