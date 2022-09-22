package com.tinvio.accounting.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBConfiguration {

    @Value("${spring.datasource.driverClassName}")
    private String dbDriver;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private int connectionTimeout;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minIdle;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maxPoolSize;

    @Value("${spring.ro.datasource.url}")
    private String roDBUrl;

    @Value("${spring.ro.datasource.username}")
    private String roUser;

    @Value("${spring.ro.datasource.password}")
    private String roPassword;

    @Autowired
    private HikariDataSource hikariDataSource;

    @Bean(value = "write_db")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource);
    }

    @Bean(value = "write_db_named_template")
    public NamedParameterJdbcTemplate writeNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(hikariDataSource);
    }

    @Bean(value = "read_db_named_template")
    public NamedParameterJdbcTemplate readNamedParameterJdbcTemplate() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("tinvio-ro-collect");
        config.setJdbcUrl(dbUrl);
        config.setUsername(user);
        config.setPassword(password);
        addCommonProperties(config);
        return new NamedParameterJdbcTemplate(new HikariDataSource(config));
    }

    @Bean(value = "read_db")
    public JdbcTemplate roJDBCTemplate() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("tinvio-ro-collect");
        config.setJdbcUrl(roDBUrl);
        config.setUsername(roUser);
        config.setPassword(roPassword);
        config.setMinimumIdle(40);
        config.setMaximumPoolSize(70);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        addCommonProperties(config);
        return new JdbcTemplate(new HikariDataSource(config));
    }

    private void addCommonProperties(HikariConfig config) {
        config.setConnectionTimeout(connectionTimeout);
        config.setMinimumIdle(minIdle);
        config.setMaximumPoolSize(maxPoolSize);
        config.setDriverClassName(dbDriver);
    }
}
