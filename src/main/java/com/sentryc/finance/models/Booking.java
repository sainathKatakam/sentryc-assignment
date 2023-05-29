package com.sentryc.finance.models;

import com.sentryc.finance.enums.CurrencyType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Booking extends BaseModel {
    @Id
    private int transactionId;
    private String type;
    private CurrencyType currency;
    private double amount;
    private int parent_id;

}
