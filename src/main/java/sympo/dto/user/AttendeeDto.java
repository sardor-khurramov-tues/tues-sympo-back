package sympo.dto.user;

public record AttendeeDto(
        String id,
        String email,
        String phone,
        String firstName,
        String lastName,
        String affiliation,
        String jobPosition,
        String degree,
        String field,
        String country,
        String presTitle,
        String presAbstract,
        String comments
) {}
