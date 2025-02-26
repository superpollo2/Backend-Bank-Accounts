package co.com.technicaltest.jpa.adapter;

import co.com.technicaltest.jpa.entity.TransferFoundsEntity;
import co.com.technicaltest.jpa.helper.AdapterOperations;
import co.com.technicaltest.jpa.repository.TransferFoundsRepository;
import co.com.technicaltest.model.transferfounds.TransferFounds;
import org.reactivecommons.utils.ObjectMapper;

public class TransferFoundsRepositoryAdapter extends AdapterOperations<TransferFounds, TransferFoundsEntity, String, TransferFoundsRepository> {
    public TransferFoundsRepositoryAdapter(TransferFoundsRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, TransferFounds.class));
    }

}
