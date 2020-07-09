package com.telegram.bot;

import com.telegram.bot.config.bot.BotConfig;
import com.telegram.bot.config.core.DatabaseConfig;
import com.telegram.bot.config.core.JpaConfig;
import com.telegram.bot.config.swagger.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.ApiContextInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableAspectJAutoProxy
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = {"com.telegram.bot"},
        exclude = {
                JacksonAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        })
@Import({
        DatabaseConfig.class,
        JpaConfig.class,
        SwaggerConfig.class,
        BotConfig.class
})
public class BotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(BotApplication.class, args);
    }

}
