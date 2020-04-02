package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.domain.exceptions.CustomerNotUniqueException;
import com.switchfully.eurder.domain.exceptions.ItemNotFoundException;
import com.switchfully.eurder.domain.exceptions.ItemNotUniqueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    protected void customerNotFoundException(CustomerNotFoundException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(CustomerNotUniqueException.class)
    protected void customerNotUniqueException(CustomerNotUniqueException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    protected void customerNotFoundException(ItemNotFoundException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(ItemNotUniqueException.class)
    protected void customerNotUniqueException(ItemNotUniqueException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


}
