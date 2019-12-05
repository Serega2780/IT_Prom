package org.test.hrdept.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.test.hrdept.util.PropertyReader;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DBHelper {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(PropertyReader.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(PropertyReader.getProperty("spring.datasource.url"));
        dataSource.setUsername(PropertyReader.getProperty("spring.datasource.username"));
        dataSource.setPassword(PropertyReader.getProperty("spring.datasource.password"));

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource(
                "data.sql"));
        try {
            Connection connection = dataSource.getConnection();
            resourceDatabasePopulator.populate(connection); // this starts the script execution, in the order as added
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", PropertyReader.getProperty("spring.jpa.database-platform"));
        hibernateProp.put("hibernate.hbm2ddl.auto", PropertyReader.getProperty("spring.jpa.hibernate.ddl-auto"));
        return hibernateProp;
    }

    @Bean
    public SessionFactory sessionFactory()
            throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("org.test.hrdept.domain");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

}
