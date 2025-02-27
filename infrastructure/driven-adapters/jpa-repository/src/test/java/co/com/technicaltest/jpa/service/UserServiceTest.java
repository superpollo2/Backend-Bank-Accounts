package co.com.technicaltest.jpa.service;

import co.com.technicaltest.jpa.entity.UserEntity;
import co.com.technicaltest.jpa.mapper.Mapper;
import co.com.technicaltest.jpa.repository.UserRepository;
import co.com.technicaltest.jpa.util.Constants;
import co.com.technicaltest.model.config.BankAccountException;
import co.com.technicaltest.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserService userService;


    @Test
    void createUserShouldReturnUserWhenUserDoesNotExist() {
        var  user = new User("12345", "John Doe");
        var userEntity = new UserEntity(UUID.randomUUID(),"12345", "John Doe");

        when(userRepository.existsUserEntityByIdentityDocument("12345")).thenReturn(false);
        when(mapper.UserDomainToEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(mapper.UserEntityToDomain(userEntity)).thenReturn(user);

        var result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("12345", result.getIdentityDocument());
        assertEquals("John Doe", result.getName());

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void createUserShouldThrowExceptionWhenUserAlreadyExists() {
        var user = new User("12345", "John Doe");

        when(userRepository.existsUserEntityByIdentityDocument("12345")).thenReturn(true);

        var exception = assertThrows(BankAccountException.class, () -> {
            userService.createUser(user);
        });

        assertEquals(HttpStatus.CONFLICT.value(), exception.getStatus());
        assertTrue(exception.getMessage().contains(Constants.USER_ALREADY_EXISTS));

        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserShouldReturnUserEntityWhenUserExists() {
        var userEntity = new UserEntity(UUID.randomUUID(), "12345", "John Doe");

        when(userRepository.findUserEntityByIdentityDocument("12345")).thenReturn(Optional.of(userEntity));

        var result = userService.getUser("12345");

        assertNotNull(result);
        assertEquals("12345", result.getIdentityDocument());
        assertEquals("John Doe", result.getName());

        verify(userRepository, times(1)).findUserEntityByIdentityDocument("12345");
    }

    @Test
    void getUserShouldThrowExceptionWhenUserDoesNotExist() {

        when(userRepository.findUserEntityByIdentityDocument("12345")).thenReturn(Optional.empty());

        var exception = assertThrows(BankAccountException.class, () -> {
            userService.getUser("12345");
        });

        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatus());
        assertTrue(exception.getMessage().contains(Constants.USER_NOT_FOUND));

        verify(userRepository, times(1)).findUserEntityByIdentityDocument("12345");
    }
}