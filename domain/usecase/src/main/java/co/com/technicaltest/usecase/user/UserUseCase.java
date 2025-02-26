package co.com.technicaltest.usecase.user;

import co.com.technicaltest.model.user.User;
import co.com.technicaltest.model.user.gateways.UserGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserGateway userGateway;

    public User createUser(User user){
        return userGateway.createUser(user);
    }
}
