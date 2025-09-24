package sympo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sympo.constant.ResponseType;
import sympo.exception.CustomBadRequestException;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class RecaptchaService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    public void validateToken(String recaptchaToken) {
        String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecret);
        map.add("response", recaptchaToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        Map<String, Object> googleResponse =
                restTemplate.postForObject(verifyUrl, request, Map.class);

        if (googleResponse == null || !(Boolean) googleResponse.get("success"))
            throw new CustomBadRequestException(ResponseType.INVALID_RECAPTCHA);

        Number score = (Number) googleResponse.get("score");

        if (score.doubleValue() < 0.5)
            throw new CustomBadRequestException(ResponseType.SUSPICIOUS_ACTIVITY_DETECTED);
    }

}
