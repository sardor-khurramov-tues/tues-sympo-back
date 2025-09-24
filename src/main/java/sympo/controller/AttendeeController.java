package sympo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sympo.constant.Endpoint;
import sympo.dto.ResponseDto;
import sympo.service.AttendeeService;
import sympo.service.RecaptchaService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(Endpoint.ATTENDEE)
public class AttendeeController {

    private final AttendeeService attendeeService;
    private final RecaptchaService recaptchaService;

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<Object>> registerAttendee(
            @RequestPart @Valid @NotBlank @Size(max = 4096) String recaptchaToken,
            @RequestPart @Valid @NotBlank @Email @Size(max = 127) String email,
            @RequestPart @Valid @NotBlank @Size(max = 15) String phone,
            @RequestPart @Valid @NotBlank @Size(max = 63) String firstName,
            @RequestPart @Valid @NotBlank @Size(max = 63) String lastName,
            @RequestPart @Valid @NotBlank @Size(max = 255) String affiliation,
            @RequestPart @Valid @NotBlank @Size(max = 255) String jobPosition,
            @RequestPart @Valid @NotBlank @Size(max = 31) String degree,
            @RequestPart @Valid @NotBlank @Size(max = 127) String field,
            @RequestPart @Valid @NotBlank @Size(max = 127) String country,
            @RequestPart(required = false) @Valid @Size(max = 127) String presTitle,
            @RequestPart(required = false) @Valid @Size(max = 2000) String presAbstract,
            @RequestPart(required = false) @Valid @Size(max = 2000) String comments,
            @RequestPart @Valid MultipartFile photo
    ) {
        recaptchaService.validateToken(recaptchaToken);

        return attendeeService.register(
                email,
                phone,
                firstName,
                lastName,
                affiliation,
                jobPosition,
                degree,
                field,
                country,
                presTitle,
                presAbstract,
                comments,
                photo
        );
    }

}
