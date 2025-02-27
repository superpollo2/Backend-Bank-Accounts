package co.com.technicaltest.jpa.repository;

import co.com.technicaltest.jpa.entity.TransferOperationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransferOperationRepository extends CrudRepository<TransferOperationEntity, UUID>, QueryByExampleExecutor<TransferOperationEntity> {
    Page<TransferOperationEntity> findByOriginAccount_AccountNumber(String originAccount, Pageable pageable);
}
