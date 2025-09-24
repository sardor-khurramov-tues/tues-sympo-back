package sympo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import sympo.constant.ResponseType;
import sympo.exception.CustomBadRequestException;

import java.util.HashMap;
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

        Map<String, String> params = new HashMap<>();
        params.put("secret", recaptchaSecret);
        params.put("response", recaptchaToken);

        Map<String, Object> googleResponse =
                restTemplate.postForObject(verifyUrl, params, Map.class);

        if (googleResponse == null || !(Boolean) googleResponse.get("success"))
            throw new CustomBadRequestException(ResponseType.INVALID_RECAPTCHA);

        double score = (Double) googleResponse.get("score");

        if (score < 0.5)
            throw new CustomBadRequestException(ResponseType.SUSPICIOUS_ACTIVITY_DETECTED);
    }

}
