package co.com.technicaltest.jpa.adapter;

import co.com.technicaltest.jpa.entity.AccountEntity;
import co.com.technicaltest.jpa.helper.AdapterOperations;
import co.com.technicaltest.jpa.repository.AccountRepository;
import co.com.technicaltest.model.account.Account;
import org.reactivecommons.utils.ObjectMapper;

public class AccountRepositoryAdapter extends AdapterOperations<Account, AccountEntity, String, AccountRepository> {
    public AccountRepositoryAdapter(AccountRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Account.class));
    }
}
