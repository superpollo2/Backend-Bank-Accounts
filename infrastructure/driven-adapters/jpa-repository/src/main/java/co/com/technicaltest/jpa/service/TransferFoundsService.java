package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.adapter.TransferFoundsRepositoryAdapter;
import co.com.technicaltest.jpa.entity.TransferOperationEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.AccountRepository;
import co.com.technicaltest.jpa.repository.TransferOperationRepository;
import co.com.technicaltest.model.enums.TransactionType;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.transferfounds.gateways.TransferFoundsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class TransferFoundsService implements TransferFoundsGateway {

    private final TransferOperationRepository transferOperationRepository;
    private final TransferFoundsRepositoryAdapter transferFoundsRepositoryAdapter;
    private final AccountRepository accountRepository;
    private final Mapper mapper;

    @Override
    public void registerTransferOperation(TransferOperation transferOperation) {
        var originAccount = accountRepository.findAccountEntityByAccountNumber(transferOperation.getOriginAccount())
                .orElseThrow(() -> new RuntimeException("Origin account does not exist"));

        var destinationAccount = accountRepository.findAccountEntityByAccountNumber(transferOperation.getDestinationAccount())
                .orElseThrow(() -> new RuntimeException("Destination account does not exist"));

        var transferOperationEntity = mapper.transferOperationDomainToEntity(transferOperation, originAccount,destinationAccount);
        transferOperationRepository.save(transferOperationEntity);
    }



    @Override
    public Page<TransferOperation> getHistoricalTransferOperations(String accountNumber, Pageable pageable) {
        Page<TransferOperationEntity> transferEntities = transferOperationRepository
                .findTransferOperationEntitiesByOriginAccount(accountNumber, pageable);

        return transferEntities.map(mapper::transferOperationEntityToDomain);
    }

}
