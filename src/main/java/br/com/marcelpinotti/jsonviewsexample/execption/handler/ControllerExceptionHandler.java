package br.com.marcelpinotti.jsonviewsexample.execption.handler;

import br.com.marcelpinotti.jsonviewsexample.execption.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception,
                                                        HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        /*
        despite not being a good practice, I added the time and date formatting in the
        method to simplify the implementation
        */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        StandardError error = new StandardError(timestamp, status.value(), "Object Not Found",
                exception.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
