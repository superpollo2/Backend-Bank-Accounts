package co.com.technicaltest.model.account.transferOperations;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WithdrawalsFunds {
    private String accountNumber;
    private BigDecimal amount;
}
