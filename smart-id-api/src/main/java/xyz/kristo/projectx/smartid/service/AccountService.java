package xyz.kristo.projectx.smartid.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.kristo.projectx.smartid.model.Account;
import xyz.kristo.projectx.smartid.model.CreateAccountRequest;
import xyz.kristo.projectx.smartid.model.FindAccountByIdRequest;

import javax.validation.Valid;

@Service
public class AccountService {
    private final RestTemplate restTemplate;
    private final String serviceUrl;

    public AccountService(RestTemplate restTemplate, @Value("${services.account}") String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    public Account findAccountById(@Valid FindAccountByIdRequest request) {
        return restTemplate.postForObject(serviceUrl+ "/find-by-id", request, Account.class);
    }

    public Account createAccount(@Valid CreateAccountRequest request) {
        return restTemplate.postForObject(serviceUrl, request, Account.class);
    }

    public void validateAccountData(@Valid CreateAccountRequest request) {
        restTemplate.postForObject(serviceUrl + "/validate", request, Account.class);
    }
}
