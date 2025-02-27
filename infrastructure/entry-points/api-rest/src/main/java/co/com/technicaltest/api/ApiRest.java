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
import co.com.technicaltest.model.util.PaginatedResult;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;


@RestController
@RequestMapping(value = Constants.PRINCIPAL_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Log4j2
public class ApiRest {

    private final ApiRestService apiRestService;

    @PostMapping(path = Constants.NEW_USER)
    public User createUser(@RequestBody User user){
        var startTime = LocalDateTime.now();
        log.info("Starting request to create user");
        try {
            return apiRestService.createUser(user);
        } finally {
            var duration = Duration.between(startTime, LocalDateTime.now());
            log.info("Request completed. Duration: {} seconds", duration.getSeconds());
        }

    }

    @PostMapping(path = Constants.NEW_ACCOUNT)
    public Account createAccount(@RequestBody NewAccount newAccount){
        log.info("neh memes"+ newAccount.toString());
        var startTime = LocalDateTime.now();
        log.info("Starting request to create account");
        try {
            return apiRestService.createAccount(newAccount);
        } finally {
            var duration = Duration.between(startTime, LocalDateTime.now());
            log.info("Request completed. Duration: {} seconds", duration.getSeconds());
        }

    }

    @PostMapping(path = Constants.TRANSFERS)
    public AccountBalance transferMoney(@RequestBody TransferFunds transferFunds){
        var startTime = LocalDateTime.now();
        log.info("Starting request to transfer money");
        try {
            return apiRestService.transferMoney(transferFunds);
        } finally {
            var duration = Duration.between(startTime, LocalDateTime.now());
            log.info("Request completed. Duration: {} seconds", duration.getSeconds());
        }
    }

    @PostMapping(path = Constants.WITHDRAWALS)
    public AccountBalance withdrawalMoney(@RequestBody WithdrawalsFunds withdrawalsFunds){
        var startTime = LocalDateTime.now();
        log.info("Starting request to withdrawal money");
        try {
            return apiRestService.withdrawalMoney(withdrawalsFunds);
        } finally {
            var duration = Duration.between(startTime, LocalDateTime.now());
            log.info("Request completed. Duration: {} seconds", duration.getSeconds());
        }
    }

    @PostMapping(path = Constants.DEPOSIT)
    public AccountBalance depositMoney(@RequestBody WithdrawalsFunds depositFunds){
        var startTime = LocalDateTime.now();
        log.info("Starting request to deposit money");
        try {
            return apiRestService.depositMoney(depositFunds);
        } finally {
            var duration = Duration.between(startTime, LocalDateTime.now());
            log.info("Request completed. Duration: {} seconds", duration.getSeconds());
        }
    }

    @GetMapping(path = Constants.CHECK_BALANCE)
    public AccountBalance getBalance(@PathVariable("accountNumber") String accountNumber){
        var startTime = LocalDateTime.now();
        log.info("Starting request to check balance of account");
        try {
            return apiRestService.getBalance(accountNumber);
        } finally {
            var duration = Duration.between(startTime, LocalDateTime.now());
            log.info("Request completed. Duration: {} seconds", duration.getSeconds());
        }

    }

    @PostMapping(path = Constants.HISTORICAL_RECORDS_TRANSFERS)
    public PaginatedResult<TransferOperation> historicalTransferOperations(@RequestBody TransferOperationHistoryPage transferOperationHistoryPage){
        var startTime = LocalDateTime.now();
        log.info("Starting request to get historical record of tranfers");
        try {
            return apiRestService.historicalTransferOperations(transferOperationHistoryPage);
        } finally {
            var duration = Duration.between(startTime, LocalDateTime.now());
            log.info("Request completed. Duration: {} seconds", duration.getSeconds());
        }
    }


}
