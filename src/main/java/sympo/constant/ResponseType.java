package sympo.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ResponseType {
    SUCCESS(0, "success"),

    INTERNAL_SERVER_ERROR(1, "internal server error"),
    NO_RESOURCE_FOUND(2, "no resource found"),
    METHOD_ARGUMENT_NOT_GIVEN(3, "method argument not given"),
    METHOD_ARGUMENT_NOT_VALID(4, "method argument not valid"),
    REQUEST_BODY_IS_MISSING(5, "required request body is missing"),
    UPLOAD_SIZE_EXCEED(6, "max file size is 5 MB"),
    WRONG_FILE_TYPE(7, "file type is not correct, please use JPEG for images"),
    VALIDATION_ERROR(8, "validation error"),

    METHOD_ARGUMENT_TYPE_MISMATCH(10, "method argument type mismatch"),
    SAVE_PDF_FAILURE(11, "failure on saving pdf file"),
    GET_PDF_FAILURE(12, "failure on getting pdf file"),

    EMAIL_IS_ALREADY_USED(1001, "email is already used"),

    NO_USER_WITH_THIS_ID(1003, "there is no user with this id"),

    BAD_REQUEST(99999, "bad request");

    private final Integer code;
    private final String message;

    ResponseType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseType getTypeByCode(String codeAsString) {
        return Arrays.stream(ResponseType.values())
                .filter(type -> type.getCode()
                                .equals(Integer.valueOf(codeAsString)))
                .findFirst()
                .orElseThrow();
    }
}
