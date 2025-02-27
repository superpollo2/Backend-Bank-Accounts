package co.com.technicaltest.model.enums;


import lombok.Getter;

@Getter
public enum TransactionType {

    DEPOSIT("DP"),
    WITHDRAWAL("RE"),
    TRANSFER("TR");

    private String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
