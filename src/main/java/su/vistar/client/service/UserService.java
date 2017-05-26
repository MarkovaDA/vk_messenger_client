package su.vistar.client.service;
import java.util.List;
import su.vistar.client.model.Company;
import su.vistar.client.model.User;


public interface UserService {		
    User findByLogin(String login);      
    List<Company> getCompanies(Integer userId);   
}