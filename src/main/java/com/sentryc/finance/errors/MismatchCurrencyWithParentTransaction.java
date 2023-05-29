package com.sentryc.finance.errors;

public class MismatchCurrencyWithParentTransaction extends Exception {
    public MismatchCurrencyWithParentTransaction() {
        super();
    }

    public MismatchCurrencyWithParentTransaction(String message) {
        super(message);
    }

    public MismatchCurrencyWithParentTransaction(String message, Throwable cause) {
        super(message, cause);
    }

    public MismatchCurrencyWithParentTransaction(Throwable cause) {
        super(cause);
    }

    protected MismatchCurrencyWithParentTransaction(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
