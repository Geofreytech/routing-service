package com.example.routingservice.exception;

// RuntimeException ensures Spring can handle it without explicit throws clauses
public class NoRouteFoundException extends RuntimeException {
    public NoRouteFoundException(String message) {
        super(message);
    }
}