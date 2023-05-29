package com.sentryc.finance.repos;

import com.sentryc.finance.enums.CurrencyType;
import com.sentryc.finance.models.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepoTest {
    @Autowired
    private BookingRepo repo;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TestEntityManager entityManager;

    private  List<Integer> actualTransactionId;

    private Booking persistedBooking ;

    @BeforeEach
    void setUp() {
         persistedBooking = Booking.builder().transactionId(1).parent_id(0).type("expenses").currency(CurrencyType.EUR).amount(100).build();
        entityManager.persist(persistedBooking);
    }

    @Test
    void SaveTransaction(){
       Booking booking = repo.findById(1).get();
       assertEquals(booking.getTransactionId(),persistedBooking.getTransactionId());

    }

}