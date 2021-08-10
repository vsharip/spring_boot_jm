package ru.sharipov.spring.spring_boot_jm.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserInCorrectData> handleException(NoSuchUserException exception) {
        UserInCorrectData userInCorrectData = new UserInCorrectData();
        userInCorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(userInCorrectData, HttpStatus.NOT_FOUND);
    }
}
