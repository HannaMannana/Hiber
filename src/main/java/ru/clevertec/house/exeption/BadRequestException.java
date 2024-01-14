package ru.clevertec.house.exeption;

public class BadRequestException extends RuntimeException{
    public BadRequestException (String message) {
        super(message);
    }
}
