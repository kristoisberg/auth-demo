package xyz.kristo.projectx.smartid.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import xyz.kristo.projectx.account.client.AccountClient;
import xyz.kristo.projectx.auth.client.AuthClient;

import javax.validation.Validator;

@Configuration
public class RestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuthClient authClient(RestTemplate restTemplate,
                                 Validator validator,
                                 @Value("${services.auth}") String serviceUrl) {
        return new AuthClient(restTemplate, validator, serviceUrl);
    }

    @Bean
    public AccountClient accountClient(RestTemplate restTemplate,
                                       Validator validator,
                                       @Value("${services.account}") String serviceUrl) {
        return new AccountClient(restTemplate, validator, serviceUrl);
    }
}
