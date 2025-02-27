package co.com.technicaltest.model.transferfounds;

import co.com.technicaltest.model.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransferOperation {
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String originAccount;
    private String destinationAccount;
}
