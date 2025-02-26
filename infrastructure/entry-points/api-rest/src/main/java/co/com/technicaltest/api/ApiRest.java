package co.com.technicaltest.api;
import co.com.technicaltest.api.service.ApiRestService;
import co.com.technicaltest.api.util.Constants;
import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.account.NewAccount;
import co.com.technicaltest.model.account.transferOperations.TransferFunds;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.account.transferOperations.WithdrawalsFunds;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API Rest controller.
 * 
 * Example of how to declare and use a use case:
 * <pre>
 * private final MyUseCase useCase;
 * 
 * public String commandName() {
 *     return useCase.execute();
 * }
 * </pre>
 */
@RestController
@RequestMapping(value = Constants.PRINCIPAL_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Log4j2
public class ApiRest {

    private final ApiRestService apiRestService;

    @PostMapping(path = Constants.NEW_USER)
    public User createUser(User user){
        return apiRestService.createUser(user);
    }

    @PostMapping(path = Constants.NEW_ACCOUNT)
    public Account createAccount(NewAccount newAccount){
        return apiRestService.createAccount(newAccount);
    }

    @PostMapping(path = Constants.TRANSFERS)
    public AccountBalance transferMoney(TransferFunds transferFunds){
        return apiRestService.transferMoney(transferFunds);
    }

    @PostMapping(path = Constants.WITHDRAWALS)
    public AccountBalance withdrawalMoney(WithdrawalsFunds withdrawalsFunds){
        return apiRestService.withdrawalMoney(withdrawalsFunds);
    }

    @PostMapping(path = Constants.DEPOSIT)
    public AccountBalance depositMoney(WithdrawalsFunds depositFunds){
        return apiRestService.depositMoney(depositFunds);
    }

    @GetMapping(path = Constants.CHECK_BALANCE)
    public AccountBalance getBalance(@PathVariable("accountNumber") String accountNumber){
        return apiRestService.getBalance(accountNumber);

    }

    @GetMapping(path = Constants.HISTORICAL_RECORDS_TRANSFERS)
    public Page<TransferOperation> historicalTransferOperations(TransferOperationHistoryPage transferOperationHistoryPage){
        return apiRestService.historicalTransferOperations(transferOperationHistoryPage);
    }


}
