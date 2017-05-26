package su.vistar.client.service;

import java.util.List;
import su.vistar.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.mapper.DBMapper;
import su.vistar.client.model.Company;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    DBMapper dataMapper;
    
    @Override
    public User findByLogin(String login) {
        return dataMapper.findUserByLogin(login);
    }
    
    @Override
    public List<Company> getCompanies(Integer userId){        
        return dataMapper.findCompany(userId);
    }
      
}
