package com.davidechiarelli.taxcodeapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ErrorDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private int code;
    private String error;
    private String exception;
    private String message;
    private String path;
    private List<String> errors;

}

