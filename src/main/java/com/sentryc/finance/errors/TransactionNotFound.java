package com.sentryc.finance.errors;

public class TransactionNotFound extends Exception {
    public TransactionNotFound() {
        super();
    }

    public TransactionNotFound(String message) {
        super(message);
    }

    public TransactionNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionNotFound(Throwable cause) {
        super(cause);
    }

    protected TransactionNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
