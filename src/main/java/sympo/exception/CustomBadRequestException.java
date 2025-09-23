package sympo.exception;

import sympo.constant.ResponseType;

public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(ResponseType responseType) {
        super(responseType.getCode().toString());
    }

}
