package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.entity.AccountEntity;
import co.com.technicaltest.jpa.entity.UserEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.AccountRepository;
import co.com.technicaltest.jpa.util.Constants;
import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.enums.AccountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccountSuccess() {
        var account = new Account("123456789", AccountType.valueOf("CURRENT"), new BigDecimal("250000"), "987654");
        var user = new UserEntity(UUID.randomUUID(), "12334", "John dee");
        var accountEntity = new AccountEntity(UUID.randomUUID(), "CURRENT", "1238395", new BigDecimal("9000"), user);

        when(accountRepository.existsAccountEntityByAccountNumber(account.getAccountNumber())).thenReturn(false);
        when(userService.getUser(account.getIdentityDocument())).thenReturn(user);
        when(mapper.accountDomainToEntity(account, user)).thenReturn(accountEntity);
        when(accountRepository.save(accountEntity)).thenReturn(accountEntity);
        when(mapper.accountEntityToDomain(accountEntity)).thenReturn(account);

        Account createdAccount = accountService.createAccount(account);
        assertNotNull(createdAccount);
        assertEquals(account.getAccountNumber(), createdAccount.getAccountNumber());
    }

    @Test
    void createAccountThrowsExceptionWhenAccountExists() {
        var account = new Account("123456789", AccountType.valueOf("CURRENT"), new BigDecimal("250000"), "987654");

        when(accountRepository.existsAccountEntityByAccountNumber(account.getAccountNumber())).thenReturn(true);

        assertThrows(BankAccountException.class, () -> accountService.createAccount(account));
    }

    @Test
    void getAccountBalance_Success() {
        var user = new UserEntity(UUID.randomUUID(), "12334", "John dee");
        var accountEntity = new AccountEntity(UUID.randomUUID(), "CURRENT", "1238395", new BigDecimal("9000"), user);
        var expectedBalance = new AccountBalance("2386501518", AccountType.valueOf("SAVINGS"), new BigDecimal("9000"));

        when(accountRepository.findAccountEntityByAccountNumber("12345")).thenReturn(Optional.of(accountEntity));
        when(mapper.accountEntityToBalanceDomain(accountEntity)).thenReturn(expectedBalance);

        AccountBalance balance = accountService.getAccountBalance("12345");
        assertNotNull(balance);
        assertEquals(new BigDecimal("9000"), balance.getBalance());
    }

    @Test
    void getAccountBalanceThrowsExceptionWhenAccountNotFound() {
        when(accountRepository.findAccountEntityByAccountNumber("12345")).thenReturn(Optional.empty());

        var exception = assertThrows(BankAccountException.class, () -> {
            accountService.getAccountBalance("12345");
        });

        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatus());
        assertTrue(exception.getMessage().contains(Constants.ACCOUNT_DOES_NOT_EXIST));

        verify(accountRepository, never()).save(any());

    }

    @Test
    void transferFundsSuccess() {
        var user = new UserEntity(UUID.randomUUID(), "12334", "John dee");
        var transferFunds = new TransferFunds("12345", "67890", new BigDecimal("200"));
        var accountEntity = new AccountEntity(UUID.randomUUID(), "CURRENT", "12345", new BigDecimal("1000"), user);
        var destinationAccount = new  AccountEntity(UUID.randomUUID(), "SAVINGS", "67890", new BigDecimal("500"), user);
        var expectedBalance = new AccountBalance("1238395", AccountType.valueOf("CURRENT"), new BigDecimal("800"));


        when(accountRepository.findAccountEntityByAccountNumber("12345")).thenReturn(Optional.of(accountEntity));
        when(accountRepository.findAccountEntityByAccountNumber("67890")).thenReturn(Optional.of(destinationAccount));
        when(mapper.transferToBalanceDomain(any())).thenReturn(expectedBalance);

        AccountBalance balance = accountService.transferFunds(transferFunds);
        assertNotNull(balance);
        assertEquals(expectedBalance.getBalance(), balance.getBalance());
    }


    @Test
    void transferFundsThrowsExceptionWhenInsufficientFunds() {
        var transferFunds = new TransferFunds("12345", "67890", new BigDecimal("2000"));
        var user = new UserEntity(UUID.randomUUID(), "12334", "John dee");
        var accountEntity = new AccountEntity(UUID.randomUUID(), "CURRENT", "12345", new BigDecimal("1000"), user);
        var destinationAccount = new  AccountEntity(UUID.randomUUID(), "SAVINGS", "67890", new BigDecimal("500"), user);

        when(accountRepository.findAccountEntityByAccountNumber("12345")).thenReturn(Optional.of(accountEntity));
        when(accountRepository.findAccountEntityByAccountNumber("67890")).thenReturn(Optional.of(destinationAccount));

        var exception = assertThrows(BankAccountException.class, () -> {
            accountService.transferFunds(transferFunds);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertTrue(exception.getMessage().contains(Constants.INSUFFICIENT_FUNDS_IN_ACCOUNT));

        verify(accountRepository, never()).save(any());


    }

    @Test
    void depositFundsSuccess() {
        var user = new UserEntity(UUID.randomUUID(), "12334", "John dee");
        var accountEntity = new AccountEntity(UUID.randomUUID(), "CURRENT", "12345", new BigDecimal("1000"), user);
        var expectedBalance = new AccountBalance("12345", AccountType.valueOf("CURRENT"), new BigDecimal("1100"));
        var withdrawalsFunds = new WithdrawalsFunds("12345", new BigDecimal("100"));


        when(accountRepository.findAccountEntityByAccountNumber("12345")).thenReturn(Optional.of(accountEntity));
        when(mapper.transferToBalanceDomain(any())).thenReturn(expectedBalance);

        AccountBalance balance = accountService.depositFunds(withdrawalsFunds);
        assertNotNull(balance);
        assertEquals(expectedBalance.getBalance(), balance.getBalance());
    }

    @Test
    void withdrawalsFundsSuccess() {
        var user = new UserEntity(UUID.randomUUID(), "12334", "John dee");
        var accountEntity = new AccountEntity(UUID.randomUUID(), "CURRENT", "12345", new BigDecimal("1000"), user);
        var expectedBalance = new AccountBalance("12345", AccountType.valueOf("CURRENT"), new BigDecimal("900"));
        var withdrawalsFunds = new WithdrawalsFunds("12345", new BigDecimal("100"));

        when(accountRepository.findAccountEntityByAccountNumber("12345")).thenReturn(Optional.of(accountEntity));
        when(mapper.transferToBalanceDomain(any())).thenReturn(expectedBalance);

        AccountBalance balance = accountService.withdrawalsFunds(withdrawalsFunds);
        assertNotNull(balance);
        assertEquals(expectedBalance.getBalance(), balance.getBalance());
    }

    @Test
    void withdrawalsFundsThrowsExceptionWhenInsufficientFunds() {
        var user = new UserEntity(UUID.randomUUID(), "12334", "John dee");
        var accountEntity = new AccountEntity(UUID.randomUUID(), "CURRENT", "12345", new BigDecimal("1000"), user);
        var withdrawalsFunds = new WithdrawalsFunds("12345", new BigDecimal("5000"));

        when(accountRepository.findAccountEntityByAccountNumber("12345")).thenReturn(Optional.of(accountEntity));

        var exception = assertThrows(BankAccountException.class, () -> {
            accountService.withdrawalsFunds(withdrawalsFunds);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertTrue(exception.getMessage().contains(Constants.INSUFFICIENT_FUNDS_IN_ACCOUNT));

        verify(accountRepository, never()).save(any());

    }
}