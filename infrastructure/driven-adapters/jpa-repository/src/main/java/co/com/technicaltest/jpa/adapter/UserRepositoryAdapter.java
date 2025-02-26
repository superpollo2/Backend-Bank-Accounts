package co.com.technicaltest.jpa.adapter;

import co.com.technicaltest.jpa.entity.UserEntity;
import co.com.technicaltest.jpa.helper.AdapterOperations;
import co.com.technicaltest.jpa.repository.UserRepository;
import co.com.technicaltest.model.user.User;
import co.com.technicaltest.model.user.gateways.UserGateway;
import org.reactivecommons.utils.ObjectMapper;

public class UserRepositoryAdapter extends AdapterOperations<User, UserEntity, String, UserRepository>
{
    public UserRepositoryAdapter(UserRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }
}
