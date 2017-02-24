package su.vistar.client.service;

import su.vistar.client.model.User;


public interface UserService {		
    User findByLogin(String login);  
    void updateAccessToken(User user);
}