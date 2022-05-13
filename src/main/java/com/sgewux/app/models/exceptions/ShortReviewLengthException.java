package com.sgewux.app.models.exceptions;

public class ShortReviewLengthException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Review should have a minimun lenght of 15 characters.";
    }
}
