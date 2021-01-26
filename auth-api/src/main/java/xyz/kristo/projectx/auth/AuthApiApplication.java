package xyz.kristo.projectx.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.WebApplicationInitializer;
import xyz.kristo.projectx.auth.util.YamlPropertySourceFactory;

@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
@PropertySource(value = "classpath:properties.yml", factory = YamlPropertySourceFactory.class)
public class AuthApiApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AuthApiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthApiApplication.class, args);
    }
}
