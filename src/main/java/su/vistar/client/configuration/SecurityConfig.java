package su.vistar.client.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
        @Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
        @Autowired
	DataSource dataSource;
        
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());                
	}
                   
	              	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	    authenticationProvider.setUserDetailsService(userDetailsService);
	    //authenticationProvider.setPasswordEncoder(passwordEncoder()); !!!включить
	    return authenticationProvider;
	}
        
        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
            tokenRepositoryImpl.setDataSource(dataSource);
            return tokenRepositoryImpl;
        }
              
	@Override
	protected void configure(HttpSecurity http) throws Exception {
            /*http.csrf().disable();        
            http.authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/tools_options/**").hasAuthority("ADMIN")
            .and().formLogin().loginPage("/login")
            .usernameParameter("login")
            .passwordParameter("password")
            .and().exceptionHandling().accessDeniedPage("/Access_Denied");*/ 
	}
}
