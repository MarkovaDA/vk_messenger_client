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
    //получение текущего авторизованного пользователя
    public  User getCurrentUser(Long uid){
       return authUserMapper.getUserByUid(uid);
    }
    //получаем все компании данного пользователя
    public List<Company> getCompanies(int userId){
        return dataMapper.findCompany(userId);
    }    
}
