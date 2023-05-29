package com.sentryc.finance.services;

import com.sentryc.finance.enums.CurrencyType;
import com.sentryc.finance.errors.DuplicateTransactionIdException;
import com.sentryc.finance.errors.MismatchCurrencyWithParentTransaction;
import com.sentryc.finance.errors.TransactionNotFound;
import com.sentryc.finance.models.Booking;
import com.sentryc.finance.repos.BookingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookingServiceTest {
    @Autowired
    private BookingService service;

    @MockBean
    BookingRepo bookingRepo;

    private List<String> listOfCurrencies = Arrays.asList("EUR","USD");

    private Booking booking;

    @BeforeEach
    void setUp() {
        Booking dummy = new Booking().builder().transactionId(1000).amount(0).currency(CurrencyType.EUR).parent_id(999).build();

         booking = new Booking().builder()
                .transactionId(1)
                .currency(CurrencyType.EUR)
                .amount(100)
                .parent_id(0)
                .type("expenses")
                .build();
        booking.setCreated_At(new Date());

    }

    @Test
    @DisplayName("Testing Saving Transaction")
    void saveTransaction() throws MismatchCurrencyWithParentTransaction, DuplicateTransactionIdException {
        Booking inputbooking = Booking.builder()
                                .transactionId(1)
                                .currency(CurrencyType.EUR)
                                .amount(100.0)
                                .parent_id(0)
                                .type("expenses")
                                .build();
        when(bookingRepo.save(inputbooking)).thenReturn(booking);
       Booking dbTransaction = service.saveTransaction(inputbooking);
       assertEquals(booking.getTransactionId() , dbTransaction.getTransactionId());
       assertNotNull(dbTransaction.getCreated_At());
    }

    @Test
    @DisplayName("Testing Currencies Used for All Transactions")
    void getAllCurrency() {
        List<String> currencies = Arrays.asList("EUR","USD");
        Mockito.when(bookingRepo.findAllCurrency())
                .thenReturn(listOfCurrencies);
       List<String> listOfCurrencies = service.getAllCurrency();

       assertEquals(currencies.size(),listOfCurrencies.size());

        for(int i=0;i<listOfCurrencies.size();i++){
            assertEquals(currencies.get(i),listOfCurrencies.get(i));
        }
    }

    @Test
    @DisplayName("Testing Transaction Ids for given Transaction type")
    void getTransactionIdsForType() {
        Mockito.when(bookingRepo.findAlTransactionsByType("expenses"))
                .thenReturn(Arrays.asList(1));
        List<Integer> listOfTrnasactions = service.getTransactionIdsForType("expenses");

        assertEquals(listOfTrnasactions.get(0), 1);
    }

    @Test
    void getSumOfAmountsForTransaction() throws TransactionNotFound {
        Mockito.when(bookingRepo.findById(1)).thenReturn(Optional.ofNullable(booking));
        Mockito.when(bookingRepo.findSumofAmountsForTransaction(1))
                .thenReturn(100.0);

        Double amount = service.getSumOfAmountsForTransaction(1);
        assertEquals(amount,100.0);

    }
}