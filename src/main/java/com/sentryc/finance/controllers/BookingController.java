package com.sentryc.finance.controllers;

import com.sentryc.finance.dto.CurrencyWithSum;
import com.sentryc.finance.enums.CurrencyType;
import com.sentryc.finance.errors.DuplicateTransactionIdException;
import com.sentryc.finance.errors.MismatchCurrencyWithParentTransaction;
import com.sentryc.finance.errors.TransactionNotFound;
import com.sentryc.finance.models.Booking;
import com.sentryc.finance.services.BookingService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.*;

@RequestMapping("/bookingservice")
@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/transaction/{id}")
    public ResponseEntity<Booking> saveTransaction(@RequestBody Booking booking, @PathVariable int id) throws DuplicateTransactionIdException, MismatchCurrencyWithParentTransaction {
        logger.info(" Transaction Details to be saved \n" + booking.toString());
        booking.setTransactionId(id);
        Booking booked = bookingService.saveTransaction(booking);
        logger.info(" Saved Transaction Details Success");
        return ResponseEntity.ok(booked);
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<String>> getAllCurrencies() {
        logger.info("Getting All Currencies used for transacitons");
        List<String> currencies = bookingService.getAllCurrency();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/types/{type}")
    public List<Integer> getTransactionIdforType(@PathVariable String type) {
        logger.info("Got Request to get Transactions Ids for txn type : " + type);
        return bookingService.getTransactionIdsForType(type);
    }

    @GetMapping("sum/{Id}")

    public double getSumOfAmountsForTransaction(@PathVariable int Id) throws TransactionNotFound {
        logger.info("Getting Total Sum For Transaction : " + Id);
        return bookingService.getSumOfAmountsForTransaction(Id);

    }

}
