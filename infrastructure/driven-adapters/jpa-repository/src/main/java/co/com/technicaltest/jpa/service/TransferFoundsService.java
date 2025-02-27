package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.entity.AccountEntity;
import co.com.technicaltest.jpa.entity.TransferOperationEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.AccountRepository;
import co.com.technicaltest.jpa.repository.TransferOperationRepository;
import co.com.technicaltest.jpa.util.Constants;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.config.BankAccountErrorCode;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.transferfounds.gateways.TransferFoundsGateway;
import co.com.technicaltest.model.util.PaginatedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@RequiredArgsConstructor
@Log
public class TransferFoundsService implements TransferFoundsGateway {

    private final TransferOperationRepository transferOperationRepository;
    private final AccountRepository accountRepository;
    private final Mapper mapper;

    @Override
    public void registerTransferOperation(TransferOperation transferOperation) {
        AccountEntity originAccount = null;
        AccountEntity destinationAccount = null;
        if(transferOperation.getOriginAccount() != null) {
            originAccount = accountRepository.findAccountEntityByAccountNumber(transferOperation.getOriginAccount())
                    .orElseThrow(() -> new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                            BankAccountErrorCode.BCB00.getErrorTitle(), Constants.ORIGIN_ACCOUNT_DOES_NOT_EXIST));
        }

        if(transferOperation.getDestinationAccount() != null) {
            destinationAccount = accountRepository.findAccountEntityByAccountNumber(transferOperation.getDestinationAccount())
                    .orElseThrow(() -> new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                            BankAccountErrorCode.BCB00.getErrorTitle(), Constants.DESTINATION_ACCOUNT_DOES_NOT_EXIST));
        }
        var transferOperationEntity = mapper.transferOperationDomainToEntity(transferOperation, originAccount,destinationAccount);
        transferOperationRepository.save(transferOperationEntity);
    }

    @Override
    public PaginatedResult<TransferOperation> getHistoricalTransferOperations(TransferOperationHistoryPage transferOperationHistoryPage) {
        var page = transferOperationHistoryPage.getPage();
        var size = transferOperationHistoryPage.getSize();
        var pageable = PageRequest.of(page, size);

        Page<TransferOperationEntity> transferEntities = transferOperationRepository
                .findByOriginAccount_AccountNumber(transferOperationHistoryPage.getAccountNumber(), pageable);


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
