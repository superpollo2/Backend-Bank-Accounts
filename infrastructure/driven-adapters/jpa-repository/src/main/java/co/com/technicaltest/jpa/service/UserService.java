package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.adapter.UserRepositoryAdapter;
import co.com.technicaltest.jpa.entity.UserEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.UserRepository;
import co.com.technicaltest.model.user.User;
import co.com.technicaltest.model.user.gateways.UserGateway;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public class UserService implements UserGateway {

    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public User createUser(User user) {
        return mapper.UserEntityToDomain(
                userRepository.save(mapper.UserDomainToEntity(user))
        );
    }

    public Optional<UserEntity> getUser(String identityDocument){
        return userRepository.findUserEntityByIdentityDocument(identityDocument);
    }

}
