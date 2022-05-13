package com.sgewux.app.models.exceptions;

public class UnsetFieldsException extends RuntimeException{

    @Override
    public String getMessage() {
        return "All fields except numOfPieces and Price. Must be set before using .build()";
    }

    
    
}
