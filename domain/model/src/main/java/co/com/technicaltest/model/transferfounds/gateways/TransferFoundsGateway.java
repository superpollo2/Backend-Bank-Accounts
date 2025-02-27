package co.com.technicaltest.model.transferfounds.gateways;

import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.util.PaginatedResult;


public interface TransferFoundsGateway {

    void registerTransferOperation(TransferOperation transferOperation);
    PaginatedResult<TransferOperation> getHistoricalTransferOperations(TransferOperationHistoryPage transferOperationHistoryPage);
}
