package xyz.kristo.projectx.account.client;

import org.springframework.web.client.RestTemplate;
import xyz.kristo.projectx.account.client.dto.Account;
import xyz.kristo.projectx.account.client.dto.CreateAccountRequest;
import xyz.kristo.projectx.common.rest.BaseRestClient;

import javax.validation.Validator;
import java.util.Map;

public class AccountClient extends BaseRestClient {
    public AccountClient(RestTemplate restTemplate, Validator validator, String serviceUrl) {
        super(restTemplate, validator, serviceUrl);
    }

    public Account findAccountById(Long accountId) {
        Map<String, ?> params = Map.of("accountId", accountId);
        return get("find-by-id?accountId={accountId}", Account.class, params);
    }

    public Account findAccountByUsernameOrEmail(String usernameOrEmail) {
        Map<String, ?> params = Map.of("usernameOrEmail", usernameOrEmail);
        return get("find-by-username-or-email?usernameOrEmail={usernameOrEmail}", Account.class, params);
    }

    public Account createAccount(CreateAccountRequest request) {
        return post("", request, Account.class);
    }

    public void validateAccountData(CreateAccountRequest request) {
        post("validate", request);
    }
}
