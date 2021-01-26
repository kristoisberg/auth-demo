package xyz.kristo.projectx.account.client;

import org.springframework.web.client.RestTemplate;
import xyz.kristo.projectx.account.client.dto.Account;
import xyz.kristo.projectx.account.client.dto.CreateAccountRequest;
import xyz.kristo.projectx.account.client.dto.FindAccountByIdRequest;
import xyz.kristo.projectx.account.client.dto.FindAccountByUsernameOrEmailRequest;
import xyz.kristo.projectx.common.rest.BaseRestClient;

import javax.validation.Validator;

public class AccountClient extends BaseRestClient {
    public AccountClient(RestTemplate restTemplate, Validator validator, String serviceUrl) {
        super(restTemplate, validator, serviceUrl);
    }

    public Account findAccountById(FindAccountByIdRequest request) {
        return post("find-by-id", request, Account.class);
    }

    public Account findAccountByUsernameOrEmail(FindAccountByUsernameOrEmailRequest request) {
        return post("find-by-username-or-email", request, Account.class);
    }

    public Account createAccount(CreateAccountRequest request) {
        return post("", request, Account.class);
    }

    public void validateAccountData(CreateAccountRequest request) {
        post("validate", request);
    }
}
