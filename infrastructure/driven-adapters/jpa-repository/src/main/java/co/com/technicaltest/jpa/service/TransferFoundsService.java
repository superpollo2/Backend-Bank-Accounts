package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.entity.TransferOperationEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.AccountRepository;
import co.com.technicaltest.jpa.repository.TransferOperationRepository;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.config.BankAccountErrorCode;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.transferfounds.gateways.TransferFoundsGateway;
import co.com.technicaltest.model.util.PaginatedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferFoundsService implements TransferFoundsGateway {

    private final TransferOperationRepository transferOperationRepository;
    private final AccountRepository accountRepository;
    private final Mapper mapper;

    @Override
    public void registerTransferOperation(TransferOperation transferOperation) {
        var originAccount = accountRepository.findAccountEntityByAccountNumber(transferOperation.getOriginAccount())
                .orElseThrow(() -> new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), "Origin account does not exist"));

        var destinationAccount = accountRepository.findAccountEntityByAccountNumber(transferOperation.getDestinationAccount())
                .orElseThrow(() -> new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), "Destination account does not exist"));

        var transferOperationEntity = mapper.transferOperationDomainToEntity(transferOperation, originAccount,destinationAccount);
        transferOperationRepository.save(transferOperationEntity);
    }

    @Override
    public PaginatedResult<TransferOperation> getHistoricalTransferOperations(TransferOperationHistoryPage transferOperationHistoryPage) {
        var page = transferOperationHistoryPage.getPage();
        var size = transferOperationHistoryPage.getSize();
        var pageable = PageRequest.of(page, size, Sort.by("date").descending());

        Page<TransferOperationEntity> transferEntities = transferOperationRepository
                .findTransferOperationEntitiesByOriginAccount(transferOperationHistoryPage.getAccountNumber(), pageable);

        var operations = transferEntities
                .map(mapper::transferOperationEntityToDomain)
                .getContent();

        return PaginatedResult.<TransferOperation>builder()
                .content(operations)
                .page(transferEntities.getNumber())
                .size(transferEntities.getSize())
                .totalElements(transferEntities.getTotalElements())
                .build();

    }


}
