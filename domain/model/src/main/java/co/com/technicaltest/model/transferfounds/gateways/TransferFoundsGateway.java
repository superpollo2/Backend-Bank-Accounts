package co.com.technicaltest.model.transferfounds.gateways;

import co.com.technicaltest.model.transferfounds.TransferOperation;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TransferFoundsGateway {

    void registerTransferOperation(TransferOperation transferOperation);
    Page<TransferOperation> getHistoricalTransferOperations(String accountNumber, Pageable pageable);
}
