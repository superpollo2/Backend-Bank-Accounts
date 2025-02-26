package co.com.technicaltest.jpa.mapper;

import co.com.technicaltest.jpa.entity.AccountEntity;
import co.com.technicaltest.jpa.entity.TransferOperationEntity;
import co.com.technicaltest.jpa.entity.UserEntity;
import co.com.technicaltest.model.account.Account;
import co.com.technicaltest.model.account.AccountBalance;
import co.com.technicaltest.model.enums.AccountType;
import co.com.technicaltest.model.enums.TransactionType;
import co.com.technicaltest.model.transferfounds.TransferOperation;
import co.com.technicaltest.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public User UserEntityToDomain(UserEntity userEntity) {
        return User.builder()
                .identityDocument(userEntity.getIdentityDocument())
                .name(userEntity.getName())
                .build();
    }

    public UserEntity UserDomainToEntity(User user){
        return UserEntity.builder()
                .identityDocument(user.getIdentityDocument())
                .name(user.getName())
                .build();
    }

    public AccountEntity accountDomainToEntity(Account account, UserEntity user){
        return AccountEntity.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType().getAccountType())
                .user(user)
                .balance(account.getBalance())
                .build();
    }

    public Account accountEntityToDomain(AccountEntity account){
        return Account.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(AccountType.valueOf(account.getAccountType()))
                .identityDocument(account.getUser().getIdentityDocument())
                .balance(account.getBalance())
                .build();
    }

    public AccountBalance  accountEntityToBalanceDomain(AccountEntity account){
        return AccountBalance.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(AccountType.valueOf(account.getAccountType()))
                .balance(account.getBalance())
                .build();
    }

    public TransferOperationEntity transferOperationDomainToEntity(TransferOperation transferOperation,
                                                                   AccountEntity originAccount,
                                                                   AccountEntity destinationAccount ){
        return TransferOperationEntity.builder()
                .originAccount(originAccount != null ? originAccount : null)
                .transactionType(transferOperation.getTransactionType().getTransactionType())
                .destinationAccount(destinationAccount != null ? destinationAccount : null)
                .amount(transferOperation.getAmount())
                .build();
    }

    public AccountBalance  transferToBalanceDomain(AccountEntity account){
        return AccountBalance.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(AccountType.valueOf(account.getAccountType()))
                .balance(account.getBalance())
                .build();
    }

    public TransferOperation transferOperationEntityToDomain(TransferOperationEntity transferOperation){
        return TransferOperation.builder()
                .transactionType(TransactionType.valueOf(transferOperation.getTransactionType()))
                .destinationAccount(transferOperation.getDestinationAccount() != null
                        ? transferOperation.getDestinationAccount().getAccountNumber()
                        : null)
                .originAccount(transferOperation.getOriginAccount() != null
                        ? transferOperation.getOriginAccount().getAccountNumber()
                        : null)
                .amount(transferOperation.getAmount())
                .build();
    }


}
