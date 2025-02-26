package co.com.technicaltest.jpa.repository;

import co.com.technicaltest.jpa.entity.TransferOperationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface TransferOperationRepository extends CrudRepository<TransferOperationEntity, UUID>, QueryByExampleExecutor<TransferOperationEntity> {


    Page<TransferOperationEntity> findTransferOperationEntitiesByOriginAccount(String originAccount, Pageable pageable);
}
