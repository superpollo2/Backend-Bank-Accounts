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
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Configuration
@Log4j2
@Order(-2)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper;
    DataBuffer dataBuffer = null;

    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        var request = exchange.getRequest();
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        try {
            throw ex;
        }catch (BankAccountException exception){
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(exception.getStatus()));
            var errors = Errors.builder()
                    .href(request.getURI().toString())
                    .status(exception.getStatus())
                    .code(exception.getCode())
                    .title(exception.getTitle())
                    .detail(exception.getMessage())
                    .id(UUID.randomUUID())
                    .build();
            dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(errors));
            log.info(errors.getId() + " Error en la solicitud " + exchange.getRequest().toString() );
        }catch (JsonParseException | JsonMappingException jsonException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            Errors errors = Errors.builder()
                    .href(request.getURI().toString())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .code("CR002")
                    .title("Error en el formato del JSON")
                    .detail("El cuerpo de la solicitud contiene un JSON inválido o mal formado.")
                    .id(UUID.randomUUID())
                    .build();
            dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(errors));
            log.info(errors.getId() + " Error en la solicitud " + exchange.getRequest().toString());
            log.error(errors.getId() + " Error: JSON inválido - " + jsonException.getMessage());
        } catch (Exception generalException) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            Errors errors = Errors.builder()
                    .href(request.getURI().toString())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .code("CR-I00")
                    .title("Error Interno de la API")
                    .detail("Ocurrió un error interno en el servidor. Por favor, intente más tarde.")
                    .id(UUID.randomUUID())
                    .build();
            dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(errors));
            log.info(errors.getId() + " Error en la solicitud " + exchange.getRequest().toString());
            log.error(errors.getId() + " Error: Excepción interna - " + generalException.getMessage());
        }
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

}
