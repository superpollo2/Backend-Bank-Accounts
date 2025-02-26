package co.com.technicaltest.model.enums;


import lombok.Getter;

@Getter
public enum AccountType {

    AHORRO("cuenta ahorros","CH"),
    CORRIENTE("cuenta corriente","CC");

    private String accountName;
    private String accountType;

    AccountType(String accountType, String accountName) {
        this.accountType = accountType;
        this.accountName = accountName;
    }
}
