package com.sentryc.finance.services;

import com.sentryc.finance.enums.CurrencyType;
import com.sentryc.finance.errors.DuplicateTransactionIdException;
import com.sentryc.finance.errors.MismatchCurrencyWithParentTransaction;
import com.sentryc.finance.errors.TransactionNotFound;
import com.sentryc.finance.models.Booking;
import com.sentryc.finance.repos.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingService {

    @Autowired
    private BookingRepo repo;

    public Booking saveTransaction(Booking booking) throws DuplicateTransactionIdException, MismatchCurrencyWithParentTransaction {
        Optional<Booking> dbBookingTxnId = repo.findById(booking.getTransactionId());
        if (dbBookingTxnId.isPresent() && dbBookingTxnId.get().getTransactionId() == booking.getTransactionId())
            throw new DuplicateTransactionIdException("Already a Transaction exist with same Id : " + dbBookingTxnId.get().getTransactionId());

        if (booking.getParent_id() != 0) {
            dbBookingTxnId = repo.findById(booking.getParent_id());
            if (dbBookingTxnId.isPresent() && dbBookingTxnId.get().getCurrency() != booking.getCurrency()) {
                throw new MismatchCurrencyWithParentTransaction("Current Transaction Currency not Matching With Parent Transaction Currency ");
            }
        }
        booking.setCreated_At(new Date());
        return repo.save(booking);
    }

    public List<String> getAllCurrency() {
        return repo.findAllCurrency();
    }


    public List<Integer> getTransactionIdsForType(String type) {
        return repo.findAlTransactionsByType(type);
    }

    public double getSumOfAmountsForTransaction(int id) throws TransactionNotFound {
        Optional<Booking> booking = repo.findById(id);
        if (!booking.isPresent()) throw new TransactionNotFound("Transaction With Id not found");
        return repo.findSumofAmountsForTransaction(id);
    }
}
