package xyz.kristo.projectx.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kristo.projectx.account.dao.AccountDao;
import xyz.kristo.projectx.account.exception.BusinessException;
import xyz.kristo.projectx.account.exception.NotFoundException;
import xyz.kristo.projectx.account.model.Account;
import xyz.kristo.projectx.account.model.CreateAccountRequest;
import xyz.kristo.projectx.account.model.FindAccountByIdRequest;
import xyz.kristo.projectx.account.model.FindAccountByUsernameOrEmailRequest;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;

    public Account findAccountByUsernameOrEmail(FindAccountByUsernameOrEmailRequest request) {
        Account account = accountDao.findAccountByUsernameOrEmail(request.getUsernameOrEmail());

        if (account == null) {
            throw new NotFoundException("Account not found!");
        }

        return account;
    }

    public Account findAccountById(FindAccountByIdRequest request) {
        Account account = accountDao.findAccountById(request.getAccountId());

        if (account == null) {
            throw new NotFoundException("Account not found!");
        }

        return account;
    }

    public Account createAccount(CreateAccountRequest request) {
        validateAccountData(request);

        Account account = new Account(request.getUsername(), request.getEmail());

        accountDao.createAccount(account);
        return account;
    }

    public void validateAccountData(CreateAccountRequest request) {
        if (accountDao.isUsernameInUse(request.getUsername())) {
            throw new BusinessException("Username is already in use by another account!");
        }

        if (accountDao.isEmailInUse(request.getEmail())) {
            throw new BusinessException("E-mail is already in use by another account!");
        }
    }
}
