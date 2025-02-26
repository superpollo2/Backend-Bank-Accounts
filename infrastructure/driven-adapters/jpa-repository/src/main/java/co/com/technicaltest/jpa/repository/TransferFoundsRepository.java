package co.com.technicaltest.jpa.repository;

import co.com.technicaltest.jpa.entity.TransferFoundsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface TransferFoundsRepository extends CrudRepository<TransferFoundsEntity, String>, QueryByExampleExecutor<TransferFoundsEntity> {
}
