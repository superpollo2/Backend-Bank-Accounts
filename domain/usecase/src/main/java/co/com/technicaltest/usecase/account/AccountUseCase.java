package co.com.technicaltest.usecase.account;

import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.gateways.AccountGateway;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.enums.TransactionType;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.transferfounds.gateways.TransferFoundsGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountUseCase {

    private final AccountGateway accountGateway;
    private final TransferFoundsGateway transferFoundsGateway;

    public AccountBalance transferFunds(TransferFunds transferFunds){
        var updateBalance = accountGateway.transferFunds(transferFunds);
        var transferOperation = TransferOperation.builder()
                .originAccount(transferFunds.getOriginAccount())
                .amount(transferFunds.getAmount())
                .destinationAccount(transferFunds.getDestinationAccount())
                .transactionType(TransactionType.TRANSFERENCIA)
                .build();
        transferFoundsGateway.registerTransferOperation(transferOperation);
        return updateBalance;
    }

    public AccountBalance withdrawalsFunds(WithdrawalsFunds withdrawalsFunds){
        var updateBalance = accountGateway.withdrawalsFunds(withdrawalsFunds);
        var transferOperation = TransferOperation.builder()
                .originAccount(withdrawalsFunds.getAccountNumber())
                .amount(withdrawalsFunds.getAmount())
                .transactionType(TransactionType.RETIRO)
                .build();
        transferFoundsGateway.registerTransferOperation(transferOperation);
        return updateBalance;
    }
}
