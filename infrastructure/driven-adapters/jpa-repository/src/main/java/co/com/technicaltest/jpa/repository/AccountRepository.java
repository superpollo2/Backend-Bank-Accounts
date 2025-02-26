package co.com.technicaltest.jpa.repository;

import co.com.technicaltest.jpa.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface AccountRepository extends CrudRepository<AccountEntity, String>, QueryByExampleExecutor<AccountEntity> {
}
