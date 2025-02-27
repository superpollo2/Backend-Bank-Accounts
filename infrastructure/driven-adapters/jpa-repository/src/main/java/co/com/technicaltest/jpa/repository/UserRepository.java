package co.com.technicaltest.jpa.repository;

import co.com.technicaltest.jpa.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID>, QueryByExampleExecutor<UserEntity> {
    Optional<UserEntity> findUserEntityByIdentityDocument(String identityDocument);
    Boolean existsUserEntityByIdentityDocument(String identifyDocument);
}
