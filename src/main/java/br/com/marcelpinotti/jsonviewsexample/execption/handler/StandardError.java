package br.com.marcelpinotti.jsonviewsexample.execption.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
