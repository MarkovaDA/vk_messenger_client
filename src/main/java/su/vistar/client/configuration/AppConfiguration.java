package su.vistar.client.configuration;


import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = "su.vistar.client")
@PropertySource("classpath:application.mail.properties")
public class AppConfiguration extends WebMvcConfigurerAdapter {
    
    @Value("${mail.auth}")
    private String mailAuth;
    
    @Value("${mail.starttls.enable}")
    private String mailStarttlsEnable;
    
    @Value("${mail.host}")
    private String mailHost;
    
    @Value("${mail.port}")
    private String mailPort;
    
    @Value("${mail.protocol}")
    private String mailProtocol;
    
    @Value("${mail.encoding}")
    private String mailEncoding;
    
    @Value("${mail.username}")
    private String mailUsername;
    
    @Value("${mail.password}")
    private String mailPassword;
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
            InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
            viewResolver.setViewClass(JstlView.class);
            viewResolver.setPrefix("/WEB-INF/views/");
            viewResolver.setSuffix(".jsp");
            registry.viewResolver(viewResolver);
    }
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static_resources/**").addResourceLocations("/static_resources/"); 
    }    

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();      
    }
    
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
            return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();   
        mailProperties.put("mail.smtp.auth", Boolean.valueOf(mailAuth));
        mailProperties.put("mail.smtp.starttls.enable", Boolean.valueOf(mailStarttlsEnable));
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(mailHost);
        mailSender.setPort(Integer.parseInt(mailPort));
        mailSender.setProtocol(mailProtocol);
        mailSender.setDefaultEncoding(mailEncoding);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);
        return mailSender;
    }
}