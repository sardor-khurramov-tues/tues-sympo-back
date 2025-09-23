package sympo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import sympo.constant.ResponseType;
import sympo.dto.ResponseDto;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ResponseDto<Object>> handleCustomBadRequestException(CustomBadRequestException exception) {
        ResponseType responseType = ResponseType.getTypeByCode(exception.getMessage());
        log.error("handleCustomBadRequestException: {}", responseType.getMessage());
        return ResponseEntity.badRequest().body(
                ResponseDto.getInstance(responseType, null)
        );
    }

    @ExceptionHandler(CustomBadFileRequestException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<Resource> handleCustomBadFileRequestException(CustomBadFileRequestException exception) {
        log.error("handleCustomBadFileRequestException: {}", exception.getMessage());
//        return ResponseEntity.badRequest().body(
//                new InputStreamResource(InputStream.nullInputStream())
//        );
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ResponseDto<Object>> handleNoResourceFoundException(NoResourceFoundException exception) {
        log.error("handleNoResourceFoundException: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(
                ResponseDto.getInstance(ResponseType.NO_RESOURCE_FOUND, null)
        );
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ResponseDto<Object>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        log.error("handleMaxUploadSizeExceededException: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(
                ResponseDto.getInstance(ResponseType.UPLOAD_SIZE_EXCEED, null)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ResponseDto<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("handleHttpMessageNotReadableException: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(
                ResponseDto.getInstance(ResponseType.REQUEST_BODY_IS_MISSING, null)
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ResponseDto<Object>> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.error("handleMissingServletRequestParameterException: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(
                new ResponseDto<>(
                        ResponseType.METHOD_ARGUMENT_NOT_GIVEN.getCode(),
                        exception.getMessage(),
                        null
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ResponseDto<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        ObjectError error = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .orElseThrow();

        String field = ((FieldError) error).getField();
        String defaultMessage = error.getDefaultMessage();
        String exceptionMessage = field + " " + defaultMessage;

        log.error("Field not valid exception: {}, {}", field, defaultMessage);

        return ResponseEntity.badRequest().body(
                new ResponseDto<>(
                        ResponseType.METHOD_ARGUMENT_NOT_VALID.getCode(),
                        exceptionMessage,
                        null
                )
        );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ResponseDto<Object>> handleHandlerMethodValidationException(HandlerMethodValidationException exception) {
        log.error("handleHandlerMethodValidationException: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(
                ResponseDto.getInstance(ResponseType.VALIDATION_ERROR, null)
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ResponseDto<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.error("handleMethodArgumentTypeMismatchException: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(
                ResponseDto.getInstance(ResponseType.METHOD_ARGUMENT_TYPE_MISMATCH, null)
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ResponseDto<Object>> handleExceptionInternal(Exception exception) {
        log.error("handleExceptionInternal: {}", exception.getMessage());
        exception.printStackTrace();
        return ResponseEntity.internalServerError().body(
                ResponseDto.getInstance(ResponseType.INTERNAL_SERVER_ERROR, null)
        );
    }

}
