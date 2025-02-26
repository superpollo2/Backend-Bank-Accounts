package co.com.technicaltest.jpa.repository;

import co.com.technicaltest.jpa.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID>, QueryByExampleExecutor<UserEntity> {
    Optional<UserEntity> findUserEntityByIdentityDocument(String identityDocument);
}
