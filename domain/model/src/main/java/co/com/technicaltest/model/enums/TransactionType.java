package co.com.technicaltest.model.enums;


import lombok.Getter;

@Getter
public enum TransactionType {

    DEPOSITO("DP"),
    RETIRO("RE"),
    TRANSFERENCIA("TR");

    private String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
