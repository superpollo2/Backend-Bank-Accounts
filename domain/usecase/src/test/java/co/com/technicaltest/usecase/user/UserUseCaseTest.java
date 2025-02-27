package co.com.technicaltest.usecase.user;

import co.com.technicaltest.model.user.User;
import co.com.technicaltest.model.user.gateways.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UserUseCase userUseCase;


    @Test
    void createUserShouldReturnCreatedUser() {

        var mockUser = new User("12334", "John dee");
        when(userGateway.createUser(mockUser)).thenReturn(mockUser);

        var result = userUseCase.createUser(mockUser);

        assertNotNull(result);
        assertEquals("John dee", result.getName());
        assertEquals("12334" , result.getIdentityDocument());

        verify(userGateway, times(1)).createUser(mockUser);
    }
}