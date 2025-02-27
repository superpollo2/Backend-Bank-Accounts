package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.entity.UserEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.UserRepository;
import co.com.technicaltest.model.config.BankAccountErrorCode;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.user.User;
import co.com.technicaltest.model.user.gateways.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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

    public UserEntity getUser(String identityDocument){
        return userRepository.findUserEntityByIdentityDocument(identityDocument).orElseThrow(() ->
                new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), "User not found"));
    }

}
