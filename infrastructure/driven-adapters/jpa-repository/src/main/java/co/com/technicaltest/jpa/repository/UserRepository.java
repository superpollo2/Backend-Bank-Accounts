package co.com.technicaltest.jpa.repository;

import co.com.technicaltest.jpa.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends CrudRepository<UserEntity, String>, QueryByExampleExecutor<UserEntity> {
}
