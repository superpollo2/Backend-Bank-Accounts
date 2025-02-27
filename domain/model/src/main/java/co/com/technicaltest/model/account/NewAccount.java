package co.com.technicaltest.model.account;

import co.com.technicaltest.model.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class NewAccount {
    private AccountType accountType;
    private BigDecimal balance;
    private String identityDocument;
}

