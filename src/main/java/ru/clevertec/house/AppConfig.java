package ru.clevertec.house;


import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@ComponentScan
@Configuration
//@PropertySource("classpath:application.yml")
@EnableTransactionManagement
public class AppConfig extends WebMvcConfigurationSupport {

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "ru.clevertec.house";

    @Value("${spring.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${spring.hibernate.show_sql}")
    private String hibernateShowSQL;

    @Value("${spring.hibernate.ddl}")
    private String hibernateDDL;

    @Value("${spring.datasource.jdbcUrl}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.liquibase.change-log}")
    private String changeLog;

    @Bean
    public DataSource dataSource(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertySourcesPlaceholderConfigurer configure = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties yamlObject = Objects.requireNonNull(yaml.getObject());
        configure.setProperties(yamlObject);
        return configure;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
//        liquibase.setChangeLog("classpath:db.changelog/db.changelog.yml");
        liquibase.setChangeLog(changeLog);
        return liquibase;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    private HibernateJpaVendorAdapter vendorAdaptor() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());

        return entityManagerFactoryBean;
    }

    private Properties jpaHibernateProperties() {

        Properties properties = new Properties();

//        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, this.hibernateDialect);
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, this.hibernateShowSQL);
        properties.put(PROPERTY_NAME_HIBERNATE_DDL_AUTO, this.hibernateDDL);

        return properties;
    }
}
