package co.com.technicaltest.model.enums;


import lombok.Getter;

@Getter
public enum AccountType {

    SAVINGS("SAVINGS"),
    CURRENT("CURRENT");

    private final String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;

    }
}
