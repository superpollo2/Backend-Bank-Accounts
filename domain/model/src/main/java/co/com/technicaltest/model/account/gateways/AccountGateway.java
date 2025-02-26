package co.com.technicaltest.model.account.gateways;

import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.NewAccount;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;

public interface AccountGateway {

    Account createAccount(Account account);
    AccountBalance getAccountBalance(String accountNumber);
    AccountBalance transferFunds(TransferFunds transferFunds);
    AccountBalance depositFunds(WithdrawalsFunds depositFunds);
    AccountBalance withdrawalsFunds(WithdrawalsFunds depositFunds);

}
