package co.com.technicaltest.model.account;

import co.com.technicaltest.model.enums.AccountType;
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
public class NewAccount {
    private AccountType accountType;
    private BigDecimal balance;
    private String identityDocument;
}

