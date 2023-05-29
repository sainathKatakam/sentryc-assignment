package com.sentryc.finance.errors;

import com.sentryc.finance.models.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class BookingServiceTransactionExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateTransactionIdException.class)
    ResponseEntity<ErrorMessage> duplicateTransactionIdException(DuplicateTransactionIdException exp,
                                                                 WebRequest req) {
        ErrorMessage error = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, exp.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);

    }

    @ExceptionHandler(MismatchCurrencyWithParentTransaction.class)
    ResponseEntity<ErrorMessage> misMatchCurrencyTransactionException(MismatchCurrencyWithParentTransaction exp) {
        ErrorMessage error = new ErrorMessage(HttpStatus.FAILED_DEPENDENCY, exp.getMessage());
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(error);
    }

    @ExceptionHandler(TransactionNotFound.class)
    ResponseEntity<ErrorMessage> transactionNotFound(TransactionNotFound exp) {
        ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND, exp.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
