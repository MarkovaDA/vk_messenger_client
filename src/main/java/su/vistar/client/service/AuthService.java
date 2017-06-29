package su.vistar.client.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import su.vistar.client.model.User;
import org.springframework.stereotype.Service;
import su.vistar.client.mapper.AuthUserMapper;
import su.vistar.client.mapper.DBMapper;
import su.vistar.client.model.Company;

@Service
public class AuthService {
    
    @Autowired
    AuthUserMapper authUserMapper;
    
    @Autowired
    DBMapper dataMapper;
    
    private static User currentUser;

    public  User getCurrentUser(Long uid){
        if (currentUser != null) 
            return currentUser;
        else currentUser = authUserMapper.getUserByUid(uid);
        return currentUser;
    }

    public List<Company> getCompanies(int userId){
        return dataMapper.findCompany(userId);
    }    
}
