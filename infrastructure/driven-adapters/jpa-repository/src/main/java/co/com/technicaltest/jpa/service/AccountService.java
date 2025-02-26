package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.adapter.AccountRepositoryAdapter;
import co.com.technicaltest.jpa.entity.AccountEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.AccountRepository;
import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.NewAccount;
import co.com.technicaltest.model.account.gateways.AccountGateway;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.enums.AccountType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class AccountService implements AccountGateway {

    private final AccountRepositoryAdapter accountRepositoryAdapter;
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final Mapper mapper;

    @Override
    public Account createAccount(Account account) {
        var user = userService.getUser(account.getIdentityDocument()).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.accountEntityToDomain(accountRepository.save(mapper.accountDomainToEntity(account, user)));
    }

    @Override
    public AccountBalance getAccountBalance(String accountNumber) {
        var originAccount = accountRepository.findAccountEntityByAccountNumber(accountNumber).orElseThrow( () ->
                new RuntimeException("Account not found"));
        return mapper.accountEntityToBalanceDomain(originAccount);
    }

    @Override
    public AccountBalance transferFunds(TransferFunds transferFunds) {
        var originAccountNumber = transferFunds.getOriginAccount();
        var destinationAccountNumber = transferFunds.getDestinationAccount();
        var amount = transferFunds.getAmount();

        var originAccount = accountRepository.findAccountEntityByAccountNumber(originAccountNumber)
                .orElseThrow(() -> new RuntimeException("Origin account does not exist"));

        var destinationAccount = accountRepository.findAccountEntityByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account does not exist"));

        validateSufficientFunds(originAccount, amount);

        originAccount.setBalance(originAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        return mapper.transferToBalanceDomain(originAccount);
    }


    @Override
    public AccountBalance depositFunds(WithdrawalsFunds depositFunds) {

        var accountNumber =  depositFunds.getAccountNumber();
        var amount =  depositFunds.getAmount();

        var originAccount = accountRepository.findAccountEntityByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        originAccount.setBalance(originAccount.getBalance().add(amount));

        accountRepository.save(originAccount);

        return mapper.transferToBalanceDomain(originAccount);
    }

    @Override
    public AccountBalance withdrawalsFunds(WithdrawalsFunds withdrawalsFunds) {
        var accountNumber =  withdrawalsFunds.getAccountNumber();
        var amount =  withdrawalsFunds.getAmount();

        var originAccount = accountRepository.findAccountEntityByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        validateSufficientFunds(originAccount, amount);
        originAccount.setBalance(originAccount.getBalance().subtract(amount));

        accountRepository.save(originAccount);

        return mapper.transferToBalanceDomain(originAccount);
    }

    private void validateSufficientFunds(AccountEntity account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds in account " + account.getAccountNumber());
        }
    }

    private Boolean existAccount(String accountNumber){
        return accountRepository.existsAccountEntityByAccountNumber(accountNumber);
    }
}
