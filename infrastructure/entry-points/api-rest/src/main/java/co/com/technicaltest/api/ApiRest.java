package co.com.technicaltest.api;
import co.com.technicaltest.api.service.ApiRestService;
import co.com.technicaltest.api.util.Constants;
import co.com.technicaltest.model.account.transferOperations.TransferOperationHistoryPage;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void createUser(){

    }

    @PostMapping(path = Constants.NEW_ACCOUNT)
    public void createAccount(){

    }

    @PostMapping(path = Constants.TRANSFERS)
    public void transferMoney(){

    }

    @PostMapping(path = Constants.WITHDRAWALS)
    public void withdrawalMoney(){

    }

    @PostMapping(path = Constants.DEPOSIT)
    public void depositMoney(){

    }

    @GetMapping(path = Constants.CHECK_BALANCE)
    public void getBalance(){

    }

    @GetMapping(path = Constants.HISTORICAL_RECORDS_TRANSFERS)
    public ResponseEntity<Page<TransferOperation>> historicalTransferOperations(TransferOperationHistoryPage transferOperationHistoryPage){
        return apiRestService.historicalTransferOperations(transferOperationHistoryPage);

    }



    @GetMapping(path = "/usecase/path")
    public String commandName() {
        return "";
    }
}
