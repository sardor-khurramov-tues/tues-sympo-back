package sympo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterAttendeeDto(
        @NotBlank
        @Email
        @Size(max = 127)
        String email,
        @NotBlank
        @Pattern(regexp = "^\\S*$")
        @Size(max = 15)
        String phone,
        @NotBlank
        @Pattern(regexp = "^\\S*$")
        @Size(max = 63)
        String firstName,
        @NotBlank
        @Pattern(regexp = "^\\S*$")
        @Size(max = 63)
        String lastName,
        @NotBlank
        @Size(max = 255)
        String affiliation,
        @NotBlank
        @Size(max = 255)
        String jobPosition,
        @NotBlank
        @Size(max = 31)
        String degree,
        @NotBlank
        @Size(max = 127)
        String field,
        @NotBlank
        @Size(max = 127)
        String country,
        @Size(max = 127)
        String presTitle,
        @Size(max = 2000)
        String presAbstract,
        @Size(max = 2000)
        String comments
) {}
