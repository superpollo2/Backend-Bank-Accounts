package co.com.technicaltest.jpa.repository;

import co.com.technicaltest.jpa.entity.AccountEntity;
import co.com.technicaltest.jpa.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, UUID>, QueryByExampleExecutor<AccountEntity> {
    Optional<AccountEntity> findAccountEntityByAccountNumber(String accountNumber);
    boolean existsAccountEntityByAccountNumber(String accountNumber);
}
