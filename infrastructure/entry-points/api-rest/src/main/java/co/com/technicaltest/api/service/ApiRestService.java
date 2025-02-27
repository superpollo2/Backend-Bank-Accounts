package co.com.technicaltest.api.service;


import co.com.technicaltest.api.util.Constants;
import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.NewAccount;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.config.BankAccountErrorCode;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.user.User;
import co.com.technicaltest.model.util.PaginatedResult;
import co.com.technicaltest.usecase.account.AccountUseCase;
import co.com.technicaltest.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiRestService {

    private final UserUseCase userUseCase;
    private final AccountUseCase accountUseCase;

    public PaginatedResult<TransferOperation> historicalTransferOperations(
            TransferOperationHistoryPage transferOperationHistoryPage){
            var page = transferOperationHistoryPage.getPage();
            var size = transferOperationHistoryPage.getSize();
        if (size <= 0) {
            throw new BankAccountException(HttpStatus.BAD_REQUEST.value(), BankAccountErrorCode.BCB01.getErrorCode(),
                    BankAccountErrorCode.BCB01.getErrorTitle(), Constants.PAGE_SIZE);
        }

        if (page < 0) {
            throw new BankAccountException(HttpStatus.BAD_REQUEST.value(), BankAccountErrorCode.BCB01.getErrorCode(),
                    BankAccountErrorCode.BCB01.getErrorTitle(),Constants.PAGE_INDEX);
        }

        return accountUseCase.getHistoricalTransferOperations(transferOperationHistoryPage);
    }

    public User createUser(User user){
        return userUseCase.createUser(user);
    }

    public Account createAccount(NewAccount newAccount){
        return accountUseCase.createAccount(newAccount);
    }

    public AccountBalance getBalance(String accountNumber){
        return accountUseCase.getAccountBalance(accountNumber);
    }

    public AccountBalance transferMoney(TransferFunds transferFunds) {
        return accountUseCase.transferFunds(transferFunds);
    }

    public AccountBalance withdrawalMoney(WithdrawalsFunds withdrawalsFunds) {
        return accountUseCase.withdrawalsFunds(withdrawalsFunds);
    }

    public AccountBalance depositMoney(WithdrawalsFunds depositFunds) {
        return accountUseCase.depositFunds(depositFunds);
    }
}
