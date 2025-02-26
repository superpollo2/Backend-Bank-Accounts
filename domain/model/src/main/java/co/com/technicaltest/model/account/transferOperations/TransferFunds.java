package co.com.technicaltest.model.account.transferOperations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransferFunds {
    private String originAccount;
    private String destinationAccount;
    private BigDecimal amount;
}


