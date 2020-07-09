package com.telegram.bot.config.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Configuration
@ConfigurationProperties("datasource")
public class DatabaseConfig {

    private String driverName;

    private String url;

    private String login;

    private String password;

    private String initialSize;

    private String maxActive;

    @Bean(value = "dataSource", destroyMethod = "close")
    @Scope("singleton")
    @Primary
    public BasicDataSource getDatasource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setInitialSize(Integer.valueOf(Objects.requireNonNull(initialSize)));
        dataSource.setUsername(login);
        dataSource.setMaxActive(Integer.valueOf(Objects.requireNonNull(maxActive)));
        return dataSource;
    }
}
