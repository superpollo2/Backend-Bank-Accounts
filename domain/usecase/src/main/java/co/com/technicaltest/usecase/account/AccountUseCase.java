package co.com.technicaltest.usecase.account;

import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.NewAccount;
import co.com.technicaltest.model.account.gateways.AccountGateway;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.config.BankAccountErrorCode;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.enums.TransactionType;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.transferfounds.gateways.TransferFoundsGateway;
import co.com.technicaltest.model.util.PaginatedResult;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Random;

@RequiredArgsConstructor
public class AccountUseCase {

    private static final BigDecimal MINIMUM_BALANCE = new BigDecimal("200000");
    private static final Integer BAD_REQUEST= 400;
    private final AccountGateway accountGateway;
    private final TransferFoundsGateway transferFoundsGateway;
    private static final Random RANDOM = new Random();

    public AccountBalance transferFunds(TransferFunds transferFunds){
        var updateBalance = accountGateway.transferFunds(transferFunds);
        var transferOperation = TransferOperation.builder()
                .originAccount(transferFunds.getOriginAccount())
                .amount(transferFunds.getAmount())
                .destinationAccount(transferFunds.getDestinationAccount())
                .transactionType(TransactionType.TRANSFER)
                .build();
        transferFoundsGateway.registerTransferOperation(transferOperation);
        return updateBalance;
    }

    public AccountBalance withdrawalsFunds(WithdrawalsFunds withdrawalsFunds){
        var updateBalance = accountGateway.withdrawalsFunds(withdrawalsFunds);
        var transferOperation = TransferOperation.builder()
                .originAccount(withdrawalsFunds.getAccountNumber())
                .amount(withdrawalsFunds.getAmount())
                .transactionType(TransactionType.WITHDRAWAL)
                .build();
        transferFoundsGateway.registerTransferOperation(transferOperation);
        return updateBalance;
    }

    public AccountBalance depositFunds(WithdrawalsFunds depositFunds){
        var updateBalance = accountGateway.depositFunds(depositFunds);
        var transferOperation = TransferOperation.builder()
                .originAccount(depositFunds.getAccountNumber())
                .amount(depositFunds.getAmount())
                .transactionType(TransactionType.DEPOSIT)
                .build();
        transferFoundsGateway.registerTransferOperation(transferOperation);
        return updateBalance;
    }

    public AccountBalance getAccountBalance(String accountNumber){
        return accountGateway.getAccountBalance(accountNumber);
    }

    public Account createAccount(NewAccount newAccount){
        if (newAccount.getBalance().compareTo(MINIMUM_BALANCE) < 0) {
            throw new BankAccountException(BAD_REQUEST, BankAccountErrorCode.BCB01.getErrorCode(),
                    BankAccountErrorCode.BCB01.getErrorTitle(), "Initial balance cannot be less than " + MINIMUM_BALANCE);
        }

        var account = Account.builder()
                .identityDocument(newAccount.getIdentityDocument())
                .accountType(newAccount.getAccountType())
                .balance(newAccount.getBalance())
                .accountNumber(generateAccountNumber())
                .build();
        return accountGateway.createAccount(account);
    }

    public PaginatedResult<TransferOperation> getHistoricalTransferOperations(TransferOperationHistoryPage transferOperationHistoryPage){
        return transferFoundsGateway.getHistoricalTransferOperations(transferOperationHistoryPage);
    }

    private String generateAccountNumber() {
        var accountNumber = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            accountNumber.append(RANDOM.nextInt(10));
        }

        return accountNumber.toString();
    }




}
