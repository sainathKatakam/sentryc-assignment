package com.sentryc.finance.errors;

public class DuplicateTransactionIdException extends Exception {
    public DuplicateTransactionIdException() {
        super();
    }

    public DuplicateTransactionIdException(String message) {
        super(message);
    }

    public DuplicateTransactionIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTransactionIdException(Throwable cause) {
        super(cause);
    }

    protected DuplicateTransactionIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
