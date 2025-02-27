package co.com.technicaltest.config.app;

import co.com.technicaltest.config.pojoerror.Errors;
import co.com.technicaltest.config.util.Constants;
import co.com.technicaltest.model.config.BankAccountException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class GlobalErrorHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(BankAccountException.class)
    public ResponseEntity<Errors> handleBankAccountException(BankAccountException exception, HttpServletRequest request) {
        Errors errors = Errors.builder()
                .href(request.getRequestURI())
                .status(exception.getStatus())
                .code(exception.getCode())
                .title(exception.getTitle())
                .detail(exception.getMessage())
                .id(UUID.randomUUID())
                .build();
        log.info(errors.getId() + Constants.ERROR_IN_REQUEST + request.getRequestURI());
        return ResponseEntity.status(exception.getStatus()).body(errors);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> handleGeneralException(Exception generalException, HttpServletRequest request) {
        Errors errors = Errors.builder()
                .href(request.getRequestURI())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(Constants.ERROR_CODE_BR_I00)
                .title(Constants.INTERNAL_API_ERROR)
                .detail(Constants.INTERNAL_SERVER_ERROR_MESSAGE)
                .id(UUID.randomUUID())
                .build();
        log.info(errors.getId() + Constants.ERROR_IN_REQUEST + request.getRequestURI());
        log.error(errors.getId() + Constants.ERROR_INTERNAL_EXCEPTION + generalException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Errors> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        Errors errors = Errors.builder()
                .href(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .code(Constants.ERROR_CODE_BR_002)
                .title(Constants.JSON_FORMAT_ERROR)
                .detail(Constants.INVALID_JSON_BODY)
                .id(UUID.randomUUID())
                .build();
        log.info(errors.getId() + Constants.ERROR_IN_REQUEST + request.getRequestURI());
        log.error(errors.getId() + Constants.ERROR_INVALID_JSON + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
