package xyz.kristo.projectx.password.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kristo.projectx.account.client.AccountClient;
import xyz.kristo.projectx.account.client.dto.Account;
import xyz.kristo.projectx.account.client.dto.CreateAccountRequest;
import xyz.kristo.projectx.auth.client.AuthClient;
import xyz.kristo.projectx.auth.client.dto.AuthenticationRequest;
import xyz.kristo.projectx.auth.client.dto.AuthenticationResponse;
import xyz.kristo.projectx.password.dao.PasswordDao;
import xyz.kristo.projectx.password.exception.BusinessException;
import xyz.kristo.projectx.password.model.PasswordAuthenticationMethod;
import xyz.kristo.projectx.password.model.PasswordLoginRequest;
import xyz.kristo.projectx.password.model.PasswordRegisterRequest;
import xyz.kristo.projectx.password.util.ArrayConverter;

import java.util.Arrays;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordDao passwordDao;
    private final AccountClient accountClient;
    private final AuthClient authClient;

    public AuthenticationResponse login(PasswordLoginRequest request) {
        Account account = accountClient.findAccountByUsernameOrEmail(request.getUsernameOrEmail());
        PasswordAuthenticationMethod method = passwordDao.findPasswordAuthenticationMethod(account.getAccountId());

        if (method == null) {
            throw new BusinessException("Account does not support password authentication!");
        }

        byte[] password = ArrayConverter.toBytes(request.getPassword());
        boolean correctPassword = BCrypt.checkpw(password, method.getPasswordHash());

        Arrays.fill(request.getPassword(), '\u0000');
        Arrays.fill(password, (byte) 0);

        if (!correctPassword) {
            throw new BusinessException("Invalid password!");
        }

        return authenticate(account);
    }

    public AuthenticationResponse register(PasswordRegisterRequest request) {
        Account account = accountClient.createAccount(
                new CreateAccountRequest(request.getUsername(), request.getEmail())
        );

        byte[] password = ArrayConverter.toBytes(request.getPassword());
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        passwordDao.createPasswordAuthenticationMethod(
                account.getAccountId(), new PasswordAuthenticationMethod(passwordHash));

        Arrays.fill(request.getPassword(), '\u0000');
        Arrays.fill(password, (byte) 0);

        return authenticate(account);
    }

    private AuthenticationResponse authenticate(Account account) {
        return authClient.authenticate(
                new AuthenticationRequest(account.getAccountId(), account.getUsername(), account.getEmail())
        );
    }
}
