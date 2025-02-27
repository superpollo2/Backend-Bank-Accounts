package co.com.technicaltest.model.enums;


import lombok.Getter;

@Getter
public enum TransactionType {

    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL"),
    TRANSFER("TRANSFER");

    private String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
