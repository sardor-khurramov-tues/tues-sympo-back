package sympo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sympo.constant.ResponseType;
import sympo.dto.ResponseDto;
import sympo.entity.AttendeeEntity;
import sympo.exception.CustomBadRequestException;
import sympo.repository.AttendeeRepository;
import sympo.util.StorageUtils;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final MapperService mapperService;

    public ResponseEntity<ResponseDto<Object>> register(
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
            String comments,
            MultipartFile photo
    ) {
        validateEmail(email);
        String filename = email + ".jpeg";
        StorageUtils.saveImage(photo, filename);

        try {
            AttendeeEntity attendee = new AttendeeEntity();

            attendee.setEmail(email);
            attendee.setPhone(phone.trim());
            attendee.setRegisterTime(LocalDateTime.now());
            attendee.setFirstName(firstName.trim());
            attendee.setLastName(lastName.trim());
            attendee.setAffiliation(affiliation.trim());
            attendee.setJobPosition(jobPosition.trim());
            attendee.setDegree(degree.trim());
            attendee.setField(field.trim());
            attendee.setCountry(country.trim());
            if (Objects.nonNull(presTitle))
                attendee.setPresTitle(presTitle.trim());
            if (Objects.nonNull(presAbstract))
                attendee.setPresAbstract(presAbstract.trim());
            if (Objects.nonNull(comments))
                attendee.setComments(comments.trim());

            attendeeRepository.save(attendee);
        } catch (Exception e) {
            StorageUtils.delete(filename);
            throw new CustomBadRequestException(ResponseType.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }



    public void validateEmail(String email) {
        if (attendeeRepository.existsByEmail(email))
            throw new CustomBadRequestException(ResponseType.EMAIL_IS_ALREADY_USED);
    }

    public AttendeeEntity getUserById(Long id) {
        try {
            return attendeeRepository.findById(id)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomBadRequestException(ResponseType.NO_USER_WITH_THIS_ID);
        }
    }

}
