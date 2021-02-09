/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.sv1.exceptions;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author Andy
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotException(ResourceNotFoundException ex,WebRequest request){
        ErrorDetalles errorDetalles=new ErrorDetalles(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetalles,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globeExceptionHandler(Exception ex, WebRequest request){
        ErrorDetalles errorDetalles=new ErrorDetalles(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetalles,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
