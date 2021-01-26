package xyz.kristo.projectx.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.kristo.projectx.account.model.Account;
import xyz.kristo.projectx.account.model.CreateAccountRequest;
import xyz.kristo.projectx.account.model.FindAccountByIdRequest;
import xyz.kristo.projectx.account.model.FindAccountByUsernameOrEmailRequest;
import xyz.kristo.projectx.account.service.AccountService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("find-by-username-or-email")
    public Account findAccountByUsernameOrEmail(@Valid @RequestBody FindAccountByUsernameOrEmailRequest request) {
        return accountService.findAccountByUsernameOrEmail(request);
    }

    @PostMapping("find-by-id")
    public Account findAccountById(@Valid @RequestBody FindAccountByIdRequest request) {
        return accountService.findAccountById(request);
    }

    @PostMapping
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @PostMapping("validate")
    public void validateAccountData(@Valid @RequestBody CreateAccountRequest request) {
        accountService.validateAccountData(request);
    }
}
