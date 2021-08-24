package org.sergei.metadata.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatasourceConfig {

    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;

    public DatasourceConfig(@Value("${datasource.driverClassName}") String driverClassName,
                            @Value("${datasource.url}") String url,
                            @Value("${datasource.username}") String username,
                            @Value("${datasource.password}") String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
