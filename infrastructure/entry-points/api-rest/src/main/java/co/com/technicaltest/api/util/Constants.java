package co.com.technicaltest.api.util;


import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String PRINCIPAL_PATH = "bankAccounts/";
    public static final String NEW_USER = "newUser";
    public static final String NEW_ACCOUNT = "newAccount";
    public static final String CHECK_BALANCE = "checkBalance/{accountNumber}";
    public static final String TRANSFERS = "transfers";
    public static final String WITHDRAWALS = "withdrawals";
    public static final String DEPOSIT = "deposit";
    public static final String HISTORICAL_RECORDS_TRANSFERS = "historicalRecordsTransfers";
}
