package su.vistar.client.configuration;
import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan("su.vistar.client.mapper")
public class MyBatisConfig {
   
    @Bean
    public BasicDataSource dataSource() {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");           
            dataSource.setUsername("dasha");
            dataSource.addConnectionProperty("useUnicode","true");
            dataSource.addConnectionProperty("characterEncoding", "utf8");
            dataSource.setUrl("jdbc:mysql://vps1.vistar.su:3306/vk_messenger_v2");
            dataSource.setPassword("dasha");
            return dataSource;
    }
      
    @Bean(name="mainManager")
    public DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            return sessionFactory;
    }  
}
