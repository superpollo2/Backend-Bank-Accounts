package co.com.technicaltest.api.service;


import co.com.technicaltest.usecase.account.AccountUseCase;
import co.com.technicaltest.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiRestService {

    private final UserUseCase userUseCase;
    private final AccountUseCase accountUseCase;


}
