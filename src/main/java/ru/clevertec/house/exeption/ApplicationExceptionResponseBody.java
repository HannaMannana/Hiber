package ru.clevertec.house.exeption;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ApplicationExceptionResponseBody {
    String message;
    HttpStatus status;
}
