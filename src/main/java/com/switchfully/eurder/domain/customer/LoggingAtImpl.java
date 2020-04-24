package com.switchfully.eurder.domain.customer;

import java.lang.annotation.Annotation;

public class LoggingAtImpl implements LoggingAnnotation {



    @Override
    public Class<? extends Annotation> annotationType() {
        return LoggingAnnotation.class;
    }


    @Override
    public String logFile() {
        return "Hello";
    }
}
