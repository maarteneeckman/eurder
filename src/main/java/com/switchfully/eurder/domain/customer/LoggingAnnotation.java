package com.switchfully.eurder.domain.customer;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LoggingAnnotation {

    String logFile();

}
