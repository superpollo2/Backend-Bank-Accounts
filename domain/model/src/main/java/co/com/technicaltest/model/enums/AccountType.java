package co.com.technicaltest.model.enums;


import lombok.Getter;

@Getter
public enum AccountType {

    SAVINGS( "SA"),
    CURRENT( "CU");

    private String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;

    }
}
