package cn.xdevops.exception.handler;

import cn.xdevops.exception.ResourceNotFoundException;
import cn.xdevops.exception.ServiceException;
import cn.xdevops.exception.response.ApiError;
import cn.xdevops.exception.response.ApiSubError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiError> handleServiceException(Exception ex, WebRequest request) {
        return handleApiError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Customize error response for ResourceNotFoundException
     * @param ex exception
     * @param request WebRequest
     * @return Customized error response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(Exception ex, WebRequest request) {
        return handleApiError(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Customize error response for @Validate Validating Path Variables and Request Parameters
     * @param ex exception
     * @param request WebRequest
     * @return Customized error response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(Exception ex, WebRequest request) {
        return handleApiError(ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * Customize error response for @Valid Validating method arguments
     * @param ex MethodArgumentNotValidException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return Customized error response
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<ApiSubError> subErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> new ApiSubError("", x.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiError error = apiError("Validation failed for arguments", status);
        error.setErrors(subErrors);

        return new ResponseEntity<>(error, headers, status);
    }

    private ResponseEntity<ApiError> handleApiError(Exception ex, HttpStatus httpStatus) {
        return handleApiError(ex, new HttpHeaders(), httpStatus);
    }

    private ResponseEntity<ApiError> handleApiError(Exception ex, HttpHeaders httpHeaders, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError(ex, httpStatus), httpHeaders, httpStatus);
    }

    private ApiError apiError(Exception ex, HttpStatus httpStatus) {
        return apiError(ex.getMessage(), httpStatus);
    }

    private ApiError apiError(String message, HttpStatus httpStatus) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .status(httpStatus.value())
                .build();
    }


}
