package com.sentryc.finance.repos;

import com.sentryc.finance.enums.CurrencyType;
import com.sentryc.finance.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {


    @Query(value = "SELECT distinct (b.transactionId) from Booking  b WHERE b.type = ?1")
    public List<Integer> findAlTransactionsByType(String type);

    @Query(value = " SELECT distinct (b.currency) FROM Booking  b ")
    public List<String> findAllCurrency();

    @Query(value = "SELECT sum(b.amount) FROM Booking  b WHERE b.transactionId = ?1 OR b.parent_id =?1")
    double findSumofAmountsForTransaction(int id);
}
