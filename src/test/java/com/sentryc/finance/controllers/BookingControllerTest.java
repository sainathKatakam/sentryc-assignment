package com.sentryc.finance.controllers;

import com.sentryc.finance.enums.CurrencyType;
import com.sentryc.finance.models.Booking;
import com.sentryc.finance.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService service;

    private Booking booking;

    private List<String> listOfCurrencies;

    private List<Integer> listOfTransactionIds;

    @BeforeEach
    void setUp() {
        booking = Booking.builder().transactionId(1).currency(CurrencyType.EUR).
                type("expenses").amount(100).parent_id(0).build();
        booking.setCreated_At(new Date());
        listOfCurrencies = Arrays.asList("EUR", "USD");
        listOfTransactionIds = Arrays.asList(1);

    }

    @Test
    @DisplayName("POST: Saving Transaction")
    void saveTransaction() throws Exception {
        Booking inputBooking = Booking.builder().transactionId(1).currency(CurrencyType.EUR).
                type("expenses").amount(100).parent_id(0).build();
        Mockito.when(service.saveTransaction(inputBooking)).thenReturn(booking);
        mockMvc.perform(MockMvcRequestBuilders.post("/bookingservice/transaction/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": 100, \"currency\": \"EUR\", \"type\": \"expenses\"}")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET: Get All Currencies")
    void getAllCurrencies() throws Exception {
        String expectedCurrencies = "[\"EUR\",\"USD\"]";
        Mockito.when(service.getAllCurrency()).thenReturn(listOfCurrencies);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bookingservice/currencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualCurrencies = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedCurrencies, actualCurrencies);
    }

    @Test
    @DisplayName("GET: Transactions for Type")
    void getTransactionIdforType() throws Exception {
        String expectedTransactionIds = "[1]";
        Mockito.when(service.getTransactionIdsForType("expenses")).thenReturn(Arrays.asList(1));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/bookingservice/types/expenses")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String actualTransactionIds = result.getResponse().getContentAsString();
        assertEquals(expectedTransactionIds, actualTransactionIds);
    }

    @Test
    @DisplayName("GET: Sum of Amount for a Transaction")
    void getSumOfAmountsForTransaction() throws Exception {
        Double expectedAmount = 100.0;
        Mockito.when(service.getSumOfAmountsForTransaction(1)).thenReturn(100.0);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/bookingservice/sum/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String amt = result.getResponse().getContentAsString();
        Double actualAmount = Double.valueOf(amt);
        assertEquals(expectedAmount, actualAmount);

    }
}