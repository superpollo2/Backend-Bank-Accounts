package co.com.technicaltest.model.config;

import lombok.Getter;

@Getter
public enum BankAccountErrorCode {

    BCB00("BC-B000", "ERROR QUERY, DOES NOT EXIST OR IS NOT AVAILABLE"),
    BCB01("BC-B01", "FORMAT ERROR"),
    BCB02("BC-B02", "REQUIRED FIELD ERROR"),
    BCB03("BC-B03", "INSUFFICIENT FUNDS IN ACCOUNT"),
    BRI00("BR-I00", "INTERNAL API ERROR");

    private String errorCode;
    private String errorTitle;

    BankAccountErrorCode(String errorCode, String errorTitle) {
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
    }
}
