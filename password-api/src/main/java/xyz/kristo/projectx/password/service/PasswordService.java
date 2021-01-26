package xyz.kristo.projectx.password.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kristo.projectx.password.dao.PasswordDao;
import xyz.kristo.projectx.password.exception.BusinessException;
import xyz.kristo.projectx.password.model.Account;
import xyz.kristo.projectx.password.model.AuthenticationRequest;
import xyz.kristo.projectx.password.model.AuthenticationResponse;
import xyz.kristo.projectx.password.model.CreateAccountRequest;
import xyz.kristo.projectx.password.model.FindAccountByUsernameOrEmailRequest;
import xyz.kristo.projectx.password.model.PasswordAuthenticationMethod;
import xyz.kristo.projectx.password.model.PasswordLoginRequest;
import xyz.kristo.projectx.password.model.PasswordRegisterRequest;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordDao passwordDao;
    private final AccountService accountService;
    private final AuthService authService;

    public AuthenticationResponse login(PasswordLoginRequest request) {
        Account account = accountService.findAccountByUsernameOrEmail(
                new FindAccountByUsernameOrEmailRequest(request.getUsernameOrEmail())
        );

        PasswordAuthenticationMethod method = passwordDao.findPasswordAuthenticationMethod(account.getAccountId());

        if (method == null) {
            throw new BusinessException("Account does not support password authentication!");
        }

        if (!BCrypt.checkpw(request.getPassword(), method.getPassword())) {
            throw new BusinessException("Invalid password!");
        }

        return authenticate(account);
    }

    public AuthenticationResponse register(PasswordRegisterRequest request) {
        Account account = accountService.createAccount(
                new CreateAccountRequest(request.getUsername(), request.getEmail())
        );

        String password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        passwordDao.createPasswordAuthenticationMethod(account.getAccountId(), new PasswordAuthenticationMethod(password));

        return authenticate(account);
    }

    private AuthenticationResponse authenticate(Account account) {
        return authService.authenticate(
                new AuthenticationRequest(account.getAccountId(), account.getUsername(), account.getEmail())
        );
    }
}
