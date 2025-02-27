package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.entity.AccountEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.AccountRepository;
import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.gateways.AccountGateway;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.config.BankAccountErrorCode;
import co.com.technicaltest.model.config.BankAccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log
@RequiredArgsConstructor
public class AccountService implements AccountGateway {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final Mapper mapper;

    @Override
    public Account createAccount(Account account) {
        log.info("que putas pasa en esta mierda "+ account.toString());
        if(Boolean.TRUE.equals(accountExist(account.getAccountNumber()))){
            throw new BankAccountException(HttpStatus.CONFLICT.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                    BankAccountErrorCode.BCB00.getErrorTitle(), "Account already exists"
            );
        }
        var user = userService.getUser(account.getIdentityDocument());
        return mapper.accountEntityToDomain(accountRepository.save(mapper.accountDomainToEntity(account, user)));
    }

    @Override
    public AccountBalance getAccountBalance(String accountNumber) {
        var originAccount = accountRepository.findAccountEntityByAccountNumber(accountNumber).orElseThrow( () ->
                new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), "Account not found"));
        return mapper.accountEntityToBalanceDomain(originAccount);
    }

    @Override
    public AccountBalance transferFunds(TransferFunds transferFunds) {
        var originAccountNumber = transferFunds.getOriginAccount();
        var destinationAccountNumber = transferFunds.getDestinationAccount();
        var amount = transferFunds.getAmount();

        var originAccount = accountRepository.findAccountEntityByAccountNumber(originAccountNumber)
                .orElseThrow(() -> new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), "Origin account does not exist"));

        var destinationAccount = accountRepository.findAccountEntityByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), "Destination account does not exist"));

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
                .orElseThrow(() -> new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), "Account does not exist"));

        originAccount.setBalance(originAccount.getBalance().add(amount));

        accountRepository.save(originAccount);

        return mapper.transferToBalanceDomain(originAccount);
    }

    @Override
    public AccountBalance withdrawalsFunds(WithdrawalsFunds withdrawalsFunds) {
        var accountNumber =  withdrawalsFunds.getAccountNumber();
        var amount =  withdrawalsFunds.getAmount();

        var originAccount = accountRepository.findAccountEntityByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), "Account does not exist"));

        validateSufficientFunds(originAccount, amount);
        originAccount.setBalance(originAccount.getBalance().subtract(amount));

        accountRepository.save(originAccount);

        return mapper.transferToBalanceDomain(originAccount);
    }

    private void validateSufficientFunds(AccountEntity account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BankAccountException(HttpStatus.BAD_REQUEST.value(), BankAccountErrorCode.BCB03.getErrorCode(),
                    BankAccountErrorCode.BCB03.getErrorTitle(), "Insufficient funds in account " + account.getAccountNumber());
        }
    }

    private Boolean accountExist(String accountNumber){
        return accountRepository.existsAccountEntityByAccountNumber(accountNumber);
    }


}
