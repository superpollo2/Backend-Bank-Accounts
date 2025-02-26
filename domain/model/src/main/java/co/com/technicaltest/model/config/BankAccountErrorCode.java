package co.com.technicaltest.model.config;

import lombok.Getter;

@Getter
public enum BankAccountErrorCode {

        CRB00("CR-B000", "ERROR CONSULTA, NO EXISTE O NO ESTA DISPONIBLE"),
        CRB01("CR-B01","ERROR EN FORMATO" ),
        CRB02("CR-B02","ERROR CAMPO REQUERIDO" ),
        CRI00("CR-I00","ERROR INTERNO DE LA API");

        private String errorCode;
        private String errorTitle;

    BankAccountErrorCode(String errorCode, String errorTitle) {
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
    }
}
