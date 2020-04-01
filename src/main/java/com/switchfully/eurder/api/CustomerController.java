package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.domain.exceptions.CustomerNotUniqueException;
import com.switchfully.eurder.service.customer.CreateCustomerDto;
import com.switchfully.eurder.service.customer.CustomerDto;
import com.switchfully.eurder.service.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping(path = "customers")
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //@PreAuthorize("true")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody CreateCustomerDto addCustomerDto) {
        return customerService.createCustomer(addCustomerDto);
    }

    /*
    error handling
     */

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

}
