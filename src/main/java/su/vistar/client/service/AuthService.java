package su.vistar.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import su.vistar.client.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    UserService userService;
    
    //получение текущего авторизованного пользователя
    public  User getCurrentUser()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) 
        {
            String login = ((UserDetails) principal).getUsername();
            User loginUser = userService.findByLogin(login);
            return  loginUser;
        }
        return null;
    }
}
