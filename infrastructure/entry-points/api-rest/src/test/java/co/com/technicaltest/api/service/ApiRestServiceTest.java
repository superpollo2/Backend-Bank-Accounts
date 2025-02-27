package co.com.technicaltest.api.service;

import co.com.technicaltest.api.util.Constants;
import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.NewAccount;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.enums.AccountType;
import co.com.technicaltest.model.enums.TransactionType;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.user.User;
import co.com.technicaltest.model.util.PaginatedResult;
import co.com.technicaltest.usecase.account.AccountUseCase;
import co.com.technicaltest.usecase.user.UserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ApiRestServiceTest {

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private AccountUseCase accountUseCase;

    @InjectMocks
    private ApiRestService apiRestService;

    @Test
    void historicalTransferOperationsShouldReturnPaginatedResult() {

        var createDate = LocalDateTime.now();
        var transferOperationHistoryPage = new TransferOperationHistoryPage("123456", 1, 10);
        var mockOperations = List.of(
                new TransferOperation(TransactionType.TRANSFER, new BigDecimal("5000"), createDate, "123456", "12345434"),
                new TransferOperation(TransactionType.WITHDRAWAL, new BigDecimal("5000"), createDate, "12345766", null)
        );

        var expectedResult = new PaginatedResult<>(mockOperations, 2, 6, 10);

        when(accountUseCase.getHistoricalTransferOperations(transferOperationHistoryPage)).thenReturn(expectedResult);

        var result = apiRestService.historicalTransferOperations(transferOperationHistoryPage);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(10, result.getTotalElements());
        assertEquals(2, result.getPage());
        assertEquals(6, result.getSize());
        assertEquals(mockOperations, result.getContent());

        verify(accountUseCase, times(1)).getHistoricalTransferOperations(transferOperationHistoryPage);
    }

    @Test
    void historicalTransferOperationsShouldThrowExceptionWhenPageSizeIsInvalid() {
        var transferOperationHistoryPage = new TransferOperationHistoryPage("123456", 1, 0);

        var exception = assertThrows(BankAccountException.class, () -> {
            apiRestService.historicalTransferOperations(transferOperationHistoryPage);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertTrue(exception.getMessage().contains(Constants.PAGE_SIZE));
    }

    @Test
    void historicalTransferOperationsShouldThrowExceptionWhenPageIndexIsNegative() {
        var transferOperationHistoryPage = new TransferOperationHistoryPage("123456", -1, 10);


        BankAccountException exception = assertThrows(BankAccountException.class, () -> {
            apiRestService.historicalTransferOperations(transferOperationHistoryPage);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertTrue(exception.getMessage().contains(Constants.PAGE_INDEX));
    }

    @Test
    void createUserShouldReturnCreatedUser() {
        var mockUser = new User("12334", "John dee");

        when(userUseCase.createUser(mockUser)).thenReturn(mockUser);

        var result = apiRestService.createUser(mockUser);

        assertNotNull(result);
        assertEquals("John dee", result.getName());
        assertEquals("12334", result.getIdentityDocument());

        verify(userUseCase, times(1)).createUser(mockUser);
    }

    @Test
    void createAccountShouldReturnCreatedAccount() {

        var newAccount = new NewAccount(AccountType.valueOf("CURRENT"), new BigDecimal("250000"), "987654");
        var expectedAccount = new Account("123456789", AccountType.valueOf("CURRENT"), new BigDecimal("250000"), "987654");

        when(accountUseCase.createAccount(newAccount)).thenReturn(expectedAccount);

        var result = apiRestService.createAccount(newAccount);

        assertNotNull(result);
        assertEquals(expectedAccount, result);

        verify(accountUseCase, times(1)).createAccount(newAccount);
    }

    @Test
    void getBalanceShouldReturnAccountBalance() {
        String accountNumber = "123456";
        var expectedBalance = new AccountBalance("123456", AccountType.valueOf("SAVINGS"), new BigDecimal("10000"));

        when(accountUseCase.getAccountBalance(accountNumber)).thenReturn(expectedBalance);

        var result = apiRestService.getBalance(accountNumber);

        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(new BigDecimal("10000"), result.getBalance());

        verify(accountUseCase, times(1)).getAccountBalance(accountNumber);
    }

    @Test
    void transferMoneyShouldReturnUpdatedBalance() {
        var transferFunds = new TransferFunds("2386501518", "2078487190", new BigDecimal("1000"));
        var expectedBalance = new AccountBalance("2386501518", AccountType.valueOf("SAVINGS"), new BigDecimal("9000"));


        when(accountUseCase.transferFunds(transferFunds)).thenReturn(expectedBalance);

        var result = apiRestService.transferMoney(transferFunds);

        assertNotNull(result);
        assertEquals(expectedBalance, result);

        verify(accountUseCase, times(1)).transferFunds(transferFunds);
    }

    @Test
    void withdrawalMoneyShouldReturnUpdatedBalance() {
        var withdrawalsFunds = new WithdrawalsFunds("2386501518", new BigDecimal("500"));
        var expectedBalance = new AccountBalance("2386501518", AccountType.valueOf("SAVINGS"), new BigDecimal("9000"));


        when(accountUseCase.withdrawalsFunds(withdrawalsFunds)).thenReturn(expectedBalance);

        var result = apiRestService.withdrawalMoney(withdrawalsFunds);

        assertNotNull(result);
        assertEquals(expectedBalance, result);

        verify(accountUseCase, times(1)).withdrawalsFunds(withdrawalsFunds);
    }

    @Test
    void depositMoney_ShouldReturnUpdatedBalance() {
        var depositFunds = new WithdrawalsFunds("2386501518", new BigDecimal("1500"));
        var expectedBalance = new AccountBalance("2386501518", AccountType.valueOf("SAVINGS"), new BigDecimal("9000"));

        when(accountUseCase.depositFunds(depositFunds)).thenReturn(expectedBalance);

        var result = apiRestService.depositMoney(depositFunds);

        assertNotNull(result);
        assertEquals(expectedBalance, result);

        verify(accountUseCase, times(1)).depositFunds(depositFunds);
    }



}