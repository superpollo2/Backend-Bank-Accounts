package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.entity.AccountEntity;
import co.com.technicaltest.jpa.entity.TransferOperationEntity;
import co.com.technicaltest.jpa.entity.UserEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.AccountRepository;
import co.com.technicaltest.jpa.repository.TransferOperationRepository;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.enums.AccountType;
import co.com.technicaltest.model.enums.TransactionType;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TransferFoundsServiceTest {

    @Mock
    private TransferOperationRepository transferOperationRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private TransferFoundsService transferFoundsService;

    /*@Test
    void registerTransferOperationShouldSaveTransferWhenAccountsExist() {
        var createDate = LocalDateTime.now();
        var userOriginEntity = new UserEntity(UUID.randomUUID(), "12345", "John Doe");
        var userDestinationEntity = new UserEntity(UUID.randomUUID(), "12345", "Pepito perez");
        var transferOperation = new TransferOperation(TransactionType.TRANSFER, new BigDecimal("5000") , createDate,"123456", "12345434");
        var originAccount = new AccountEntity(UUID.randomUUID(),"SAVINGS", "12356", new BigDecimal("5000"), userOriginEntity);
        var destinationAccount = new AccountEntity(UUID.randomUUID(), "CURRENT", "1238395", new BigDecimal("9000"), userDestinationEntity);
        var transferEntity = new TransferOperationEntity();

        when(accountRepository.findAccountEntityByAccountNumber("12356")).thenReturn(Optional.of(originAccount));
        when(accountRepository.findAccountEntityByAccountNumber("1238395")).thenReturn(Optional.of(destinationAccount));
        when(mapper.transferOperationDomainToEntity(transferOperation, originAccount, destinationAccount)).thenReturn(transferEntity);

        transferFoundsService.registerTransferOperation(transferOperation);

        verify(transferOperationRepository, times(1)).save(transferEntity);
    }*/
}