package xyz.kristo.projectx.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.kristo.projectx.account.model.Account;
import xyz.kristo.projectx.account.model.CreateAccountRequest;
import xyz.kristo.projectx.account.service.AccountService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("find-by-username-or-email")
    public Account findAccountByUsernameOrEmail(@RequestParam String usernameOrEmail) {
        return accountService.findAccountByUsernameOrEmail(usernameOrEmail);
    }

    @GetMapping("find-by-id")
    public Account findAccountById(@RequestParam Long accountId) {
        return accountService.findAccountById(accountId);
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
