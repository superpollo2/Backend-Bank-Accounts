package co.com.technicaltest.config.app;

import co.com.technicaltest.config.pojoerror.Errors;
import co.com.technicaltest.model.config.BankAccountException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
        log.info(errors.getId() + " Error en la solicitud " + request.getRequestURI());
        return ResponseEntity.status(exception.getStatus()).body(errors);
    }

    @ExceptionHandler({JsonParseException.class, JsonMappingException.class})
    public ResponseEntity<Errors> handleJsonException(Exception jsonException, HttpServletRequest request) {
        Errors errors = Errors.builder()
                .href(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .code("CR002")
                .title("Error en el formato del JSON")
                .detail("El cuerpo de la solicitud contiene un JSON inválido o mal formado.")
                .id(UUID.randomUUID())
                .build();
        log.info(errors.getId() + " Error en la solicitud " + request.getRequestURI());
        log.error(errors.getId() + " Error: JSON inválido - " + jsonException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> handleGeneralException(Exception generalException, HttpServletRequest request) {
        Errors errors = Errors.builder()
                .href(request.getRequestURI())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("CR-I00")
                .title("Error Interno de la API")
                .detail("Ocurrió un error interno en el servidor. Por favor, intente más tarde.")
                .id(UUID.randomUUID())
                .build();
        log.info(errors.getId() + " Error en la solicitud " + request.getRequestURI());
        log.error(errors.getId() + " Error: Excepción interna - " + generalException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}

