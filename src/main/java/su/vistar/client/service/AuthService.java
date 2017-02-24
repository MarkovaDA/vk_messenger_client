package su.vistar.client.service;

import su.vistar.client.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class AuthService {
    //получение текущего авторизованного пользователя
    public static User getCurrentUser(UserService service)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) 
        {
            String login = ((UserDetails) principal).getUsername();
            User loginUser = service.findByLogin(login);
            return  loginUser;
        }
        return null;
    }
}
