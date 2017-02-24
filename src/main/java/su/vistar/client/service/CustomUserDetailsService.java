package su.vistar.client.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.client.model.User;
import su.vistar.client.mapper.DBMapper;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserServiceImpl userService; //UserServiceImpl

    @Autowired
    private DBMapper dataBaseMapper;

    //получение информации об авторизованном пользователе
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
            User user = userService.findByLogin(login);
            System.out.println("LOGIN: " +login);

            if(user==null){
                System.out.println("User not found");
                throw new UsernameNotFoundException("Username not found"); 
            }
            org.springframework.security.core.userdetails.User current = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), 
                    user.getStatus().equals("ACTIVE"), true, true, true, getGrantedAuthorities(user));
            return current;
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();	
            System.out.println("ROLE:" + dataBaseMapper.roleByLogin(user.getLogin()));
            authorities.add(new SimpleGrantedAuthority(dataBaseMapper.roleByLogin(user.getLogin())));
            return authorities;
    }	
}
