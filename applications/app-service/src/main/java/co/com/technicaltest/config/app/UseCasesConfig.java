package co.com.technicaltest.config.app;


import co.com.technicaltest.model.account.gateways.AccountGateway;
import co.com.technicaltest.model.transferfounds.gateways.TransferFoundsGateway;
import co.com.technicaltest.model.user.gateways.UserGateway;
import co.com.technicaltest.usecase.account.AccountUseCase;
import co.com.technicaltest.usecase.user.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;



@Configuration
@ComponentScan(basePackages = "co.com.technicaltest.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    @Bean
    public UserUseCase userUseCase(UserGateway userGateway) {
        return new UserUseCase(userGateway);
    }

    @Bean
    public AccountUseCase accountUseCase(AccountGateway accountGateway,
                                         TransferFoundsGateway transferFoundsGateway
                                         ) {
        return new AccountUseCase(accountGateway, transferFoundsGateway);
    }





}
