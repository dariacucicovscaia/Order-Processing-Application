package com.stefanini.orderprocessing.helper;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Configuration for the connection to the database
 */
@Configuration
@ComponentScan("com.stefanini.orderprocessing")
public class JDBCConfiguration {
    private final Environment environment;

    protected JDBCConfiguration(Environment environment) {
        this.environment = environment;
    }
    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setPassword(environment.getProperty("SPRING_DATASOURCE_PASSWORD"));
        dataSource.setUser(environment.getProperty("SPRING_DATASOURCE_USERNAME"));
        dataSource.setUrl(environment.getProperty("SPRING_DATASOURCE_URL"));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}
