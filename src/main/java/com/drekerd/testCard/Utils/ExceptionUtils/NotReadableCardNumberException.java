package com.drekerd.testCard.Utils.ExceptionUtils;

public class NotReadableCardNumberException extends RuntimeException {

    public NotReadableCardNumberException(String message) {
        super(message);
    }

}
