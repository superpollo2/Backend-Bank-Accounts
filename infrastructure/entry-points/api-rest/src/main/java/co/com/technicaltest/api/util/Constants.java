package co.com.technicaltest.api.util;


import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String PRINCIPAL_PATH = "/api/v1/bankAccounts/";
    public static final String NEW_USER = "newUser";
    public static final String NEW_ACCOUNT = "newAccount";
    public static final String CHECK_BALANCE = "checkBalance/{accountNumber}";
    public static final String TRANSFERS = "transfers";
    public static final String WITHDRAWALS = "withdrawals";
    public static final String DEPOSIT = "deposit";
    public static final String HISTORICAL_RECORDS_TRANSFERS = "historicalRecordsTransfers";
    public static final String PAGE_INDEX = "Page index must be zero or greater";
    public static final String PAGE_SIZE = "Page size must be greater than zero";

}
