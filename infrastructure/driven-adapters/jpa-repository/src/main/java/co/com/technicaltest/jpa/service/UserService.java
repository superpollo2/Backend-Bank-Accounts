package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.entity.UserEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.UserRepository;
import co.com.technicaltest.jpa.util.Constants;
import co.com.technicaltest.model.config.BankAccountErrorCode;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.user.User;
import co.com.technicaltest.model.user.gateways.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserGateway {

    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public User createUser(User user) {
        if(Boolean.TRUE.equals(userExist(user.getIdentityDocument()))){
            throw new BankAccountException(HttpStatus.CONFLICT.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                    BankAccountErrorCode.BCB00.getErrorTitle(), Constants.USER_ALREADY_EXISTS
            );
        }
        return mapper.UserEntityToDomain(
                userRepository.save(mapper.UserDomainToEntity(user))
        );
    }

    public UserEntity getUser(String identityDocument){
        return userRepository.findUserEntityByIdentityDocument(identityDocument).orElseThrow(() ->
                new BankAccountException(HttpStatus.NOT_FOUND.value(), BankAccountErrorCode.BCB00.getErrorCode(),
                        BankAccountErrorCode.BCB00.getErrorTitle(), Constants.USER_NOT_FOUND));
    }

    private Boolean userExist(String identityDocument){
        return userRepository.existsUserEntityByIdentityDocument(identityDocument);
    }

}
