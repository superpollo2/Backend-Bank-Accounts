package co.com.technicaltest.jpa.adapter;

import co.com.technicaltest.jpa.entity.TransferOperationEntity;
import co.com.technicaltest.jpa.helper.AdapterOperations;
import co.com.technicaltest.jpa.repository.TransferOperationRepository;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import org.reactivecommons.utils.ObjectMapper;

public class TransferFoundsRepositoryAdapter extends AdapterOperations<TransferFunds, TransferOperationEntity, String, TransferOperationRepository>
{
    public TransferFoundsRepositoryAdapter(TransferOperationRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, TransferFunds.class));
    }

}
