package com.acs560.dataanalyzer.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

import org.springframework.core.Ordered;

/**
 * The EcomSalesAnalyzer common exception handler.
 * Handles exceptions that are thrown across the application.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EcomSalesExceptionHandler {

    /**
     * Handles NoSuchElementException, which occurs when an element is not found.
     *
     * @return ResponseEntity with status 404 (Not Found) and error message.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException() {
        return ResponseEntity.status(404).body("No matching element found.");
    }

    /**
     * Handles IllegalArgumentException, which occurs when an invalid argument is passed.
     *
     * @return ResponseEntity with status 400 (Bad Request) and error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().body("Invalid argument provided.");
    }
    
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<String> handle(NoResourceFoundException ex){
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Endpoint does not exist");
	}

    /**
     * Handles generic exceptions, providing a fallback handler for unhandled exceptions.
     *
     * @param ex the exception that was thrown.
     * @return ResponseEntity with status 500 (Internal Server Error) and error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.internalServerError().body("An unexpected error occurred: " + ex.getMessage());
    }
}