package su.vistar.client.service;

import su.vistar.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.mapper.DBMapper;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    DBMapper dataMapper;
    
    @Override
    public User findByLogin(String login) {
        return dataMapper.findUserByLogin(login);
    }

    @Override
    public void updateAccessToken(User user) {
        dataMapper.updateAccessToken(user);
    }
    
}
