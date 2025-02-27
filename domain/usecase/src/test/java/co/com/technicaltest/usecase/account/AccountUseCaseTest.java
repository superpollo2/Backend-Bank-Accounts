package co.com.technicaltest.usecase.account;

import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.NewAccount;
import co.com.technicaltest.model.account.gateways.AccountGateway;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.enums.AccountType;
import co.com.technicaltest.model.enums.TransactionType;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.transferfounds.gateways.TransferFoundsGateway;
import co.com.technicaltest.model.util.PaginatedResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountUseCaseTest {

    @Mock
    private AccountGateway accountGateway;

    @Mock
    private TransferFoundsGateway transferFoundsGateway;

    @InjectMocks
    private AccountUseCase accountUseCase;

    @Test
    void transferFundsShouldReturnUpdatedBalance() {

        var transferFunds = new TransferFunds("2386501518", "2078487190", new BigDecimal("1000"));
        var expectedBalance = new AccountBalance("2386501518", AccountType.valueOf("SAVINGS"), new BigDecimal("9000"));

        when(accountGateway.transferFunds(transferFunds)).thenReturn(expectedBalance);

        var result = accountUseCase.transferFunds(transferFunds);

        assertNotNull(result);
        assertEquals(expectedBalance, result);
        verify(accountGateway, times(1)).transferFunds(transferFunds);
        verify(transferFoundsGateway, times(1)).registerTransferOperation(any(TransferOperation.class));
    }

    @Test
    void withdrawalsFundsShouldReturnUpdatedBalance() {

        var withdrawalsFunds = new WithdrawalsFunds("2386501518", new BigDecimal("500"));
        var expectedBalance = new AccountBalance("2386501518", AccountType.valueOf("SAVINGS"), new BigDecimal("9000"));


        when(accountGateway.withdrawalsFunds(withdrawalsFunds)).thenReturn(expectedBalance);

        var result = accountUseCase.withdrawalsFunds(withdrawalsFunds);

        assertNotNull(result);
        assertEquals(expectedBalance, result);
        verify(accountGateway, times(1)).withdrawalsFunds(withdrawalsFunds);
        verify(transferFoundsGateway, times(1)).registerTransferOperation(any(TransferOperation.class));
    }


    @Test
    void depositFundsShouldReturnUpdatedBalance() {
        var depositFunds = new WithdrawalsFunds("2386501518", new BigDecimal("1500"));
        var expectedBalance = new AccountBalance("2386501518", AccountType.valueOf("SAVINGS"), new BigDecimal("9000"));

        when(accountGateway.depositFunds(depositFunds)).thenReturn(expectedBalance);

        var result = accountUseCase.depositFunds(depositFunds);

        assertNotNull(result);
        assertEquals(expectedBalance, result);
        verify(accountGateway, times(1)).depositFunds(depositFunds);
        verify(transferFoundsGateway, times(1)).registerTransferOperation(any(TransferOperation.class));
    }

    @Test
    void createAccountShouldThrowExceptionWhenBalanceIsTooLow() {

        var newAccount = new NewAccount(AccountType.valueOf("CURRENT"), new BigDecimal("100000"), "987654");

        var exception = assertThrows(BankAccountException.class, () -> {
            accountUseCase.createAccount(newAccount);
        });

        assertEquals("BC-B01", exception.getCode());
        assertEquals(400, exception.getStatus());
        assertTrue(exception.getMessage().contains("Initial balance cannot be less than"));
        verify(accountGateway, never()).createAccount(any());
    }

    @Test
    void createAccountShouldReturnCreatedAccountWhenBalanceIsValid() {
        var newAccount = new NewAccount(AccountType.valueOf("CURRENT"), new BigDecimal("250000"), "987654");
        var expectedAccount = new Account("123456789", AccountType.valueOf("CURRENT"), new BigDecimal("250000"), "987654");

        when(accountGateway.createAccount(any(Account.class))).thenReturn(expectedAccount);

        var result = accountUseCase.createAccount(newAccount);

        assertNotNull(result);
        assertEquals(expectedAccount, result);
        verify(accountGateway, times(1)).createAccount(any(Account.class));
    }

    @Test
    void getAccountBalanceShouldReturnCorrectBalance() {
        var accountNumber = "123456";
        var expectedBalance = new AccountBalance("123456", AccountType.valueOf("SAVINGS"), new BigDecimal("10000"));

        when(accountGateway.getAccountBalance(accountNumber)).thenReturn(expectedBalance);

        var result = accountUseCase.getAccountBalance(accountNumber);

        assertNotNull(result);
        assertEquals("123456", result.getAccountNumber());
        assertEquals(new BigDecimal("10000"), result.getBalance());

        verify(accountGateway, times(1)).getAccountBalance(accountNumber);
    }

    @Test
    void getHistoricalTransferOperations_ShouldReturnPaginatedResult() {
        var createDate = LocalDateTime.now();
        var transferOperationHistoryPage = new TransferOperationHistoryPage("123456", 1, 10);
        var mockOperations = List.of(
                new TransferOperation(TransactionType.TRANSFER, new BigDecimal("5000") , createDate,"123456", "12345434"),
                new TransferOperation(TransactionType.WITHDRAWAL, new BigDecimal("5000") , createDate,"12345766", null)
        );

        var expectedResult = new PaginatedResult<>(mockOperations, 0, 5, 10);

        when(transferFoundsGateway.getHistoricalTransferOperations(transferOperationHistoryPage))
                .thenReturn(expectedResult);

        PaginatedResult<TransferOperation> result = accountUseCase.getHistoricalTransferOperations(transferOperationHistoryPage);


        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(10, result.getTotalElements());
        assertEquals(0, result.getPage());
        assertEquals(5, result.getSize());
        assertEquals(mockOperations, result.getContent());

        verify(transferFoundsGateway, times(1)).getHistoricalTransferOperations(transferOperationHistoryPage);
    }
}