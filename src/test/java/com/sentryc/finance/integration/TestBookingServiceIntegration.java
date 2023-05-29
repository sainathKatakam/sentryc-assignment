package com.sentryc.finance.integration;


import com.sentryc.finance.enums.CurrencyType;
import com.sentryc.finance.models.Booking;
import com.sentryc.finance.repos.BookingRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestBookingServiceIntegration {

    @LocalServerPort
    private int port;

    private static RestTemplate restTemplate;

    private String baseUrl;

    @Autowired
    private BookingRepo repo;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void getUrl() {

        baseUrl = "http://localhost".concat(":").concat(port + "").concat("/bookingservice");

    }

    @Test
    @Order(1)
    public void TestSavingTransaction() {
        Booking booking = Booking.builder()
                .currency(CurrencyType.EUR)
                .type("expenses")
                .amount(100.0)
                .parent_id(0)
                .build();
        String url = baseUrl.concat("/transaction/999");
        System.out.println("------" + url + "---------");
        Booking response = restTemplate.postForObject(url, booking, Booking.class);
        Assertions.assertEquals(response.getTransactionId(), 999);
        Assertions.assertNotNull(response.getCreated_At());
        Assertions.assertEquals(repo.findAll().size(), 999);
    }

    @Test
    @Sql(statements = "INSERT INTO BOOKING (TRANSACTION_ID,AMOUNT,CURRENCY,PARENT_ID,TYPE) " +
            "VALUES(1,100.0,1,0,'expenses')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE * from BOOKING WHERE transaction_id = 1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void TestAllCurrencies() {
        String url = baseUrl.concat("/currencies");
        List<String> response = restTemplate.getForObject(url, List.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.size(), 1);
        Assert.assertEquals(response.get(0), "EUR");
    }

    @Test
    @Sql(statements = "INSERT INTO BOOKING (TRANSACTION_ID,AMOUNT,CURRENCY,PARENT_ID,TYPE) " +
            "VALUES(1,100.0,1,0,'expenses')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BOOKING WHERE transaction_id = 1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void TestWhengivenType_ThenGetTransactionId() {
        String url = baseUrl.concat("/types/expenses");
        List<Integer> response = restTemplate.getForObject(url, List.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.size(), 1);
        int transactionId = response.get(0).intValue();
        Assert.assertEquals(1, transactionId);

    }


    @Test
    @Sql(statements = "INSERT INTO BOOKING (TRANSACTION_ID,AMOUNT,CURRENCY,PARENT_ID,TYPE) " +
            "VALUES(1,100.0,1,0,'expenses')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE  from BOOKING WHERE transaction_id = 1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void TestWhenGiveTransactionId_ThenGetSumofAmount() {
        String url = baseUrl.concat("/sum/1");
        Double response = restTemplate.getForObject(url, Double.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.doubleValue(), 100.0);

    }
}
