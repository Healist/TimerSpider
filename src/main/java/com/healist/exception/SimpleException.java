package com.healist.exception;

/**
 * Created by Healist on 2017/1/28.
 */
public class SimpleException extends RuntimeException {
    public SimpleException(String message) {
        super(message);
    }

    public SimpleException(String message, Throwable cause) {
        super(message, cause);
    }
}
