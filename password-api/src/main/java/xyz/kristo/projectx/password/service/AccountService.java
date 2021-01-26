package xyz.kristo.projectx.password.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.kristo.projectx.password.model.Account;
import xyz.kristo.projectx.password.model.CreateAccountRequest;
import xyz.kristo.projectx.password.model.FindAccountByUsernameOrEmailRequest;

import javax.validation.Validator;

@Service
public class AccountService extends RestService {
    public AccountService(RestTemplate restTemplate,
                          Validator validator,
                          @Value("${services.account}") String serviceUrl) {
        super(restTemplate, validator, serviceUrl);
    }

    public Account findAccountByUsernameOrEmail(FindAccountByUsernameOrEmailRequest request) {
        return post("find-by-username-or-email", request, Account.class);
    }

    public Account createAccount(CreateAccountRequest request) {
        return post("", request, Account.class);
    }
}
