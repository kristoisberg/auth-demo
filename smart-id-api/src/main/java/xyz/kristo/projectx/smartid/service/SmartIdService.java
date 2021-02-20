package xyz.kristo.projectx.smartid.service;

import ee.sk.smartid.AuthenticationHash;
import ee.sk.smartid.AuthenticationIdentity;
import ee.sk.smartid.AuthenticationRequestBuilder;
import ee.sk.smartid.AuthenticationResponseValidator;
import ee.sk.smartid.HashType;
import ee.sk.smartid.SmartIdAuthenticationResponse;
import ee.sk.smartid.SmartIdClient;
import ee.sk.smartid.exception.SmartIdException;
import ee.sk.smartid.exception.permanent.ServerMaintenanceException;
import ee.sk.smartid.exception.useraccount.DocumentUnusableException;
import ee.sk.smartid.exception.useraccount.UserAccountNotFoundException;
import ee.sk.smartid.exception.useraction.SessionTimeoutException;
import ee.sk.smartid.exception.useraction.UserRefusedException;
import ee.sk.smartid.rest.SmartIdRestConnector;
import ee.sk.smartid.rest.dao.Interaction;
import ee.sk.smartid.rest.dao.SemanticsIdentifier;
import ee.sk.smartid.rest.dao.SessionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kristo.projectx.account.client.AccountClient;
import xyz.kristo.projectx.account.client.dto.Account;
import xyz.kristo.projectx.account.client.dto.CreateAccountRequest;
import xyz.kristo.projectx.auth.client.AuthClient;
import xyz.kristo.projectx.auth.client.dto.AuthenticationRequest;
import xyz.kristo.projectx.auth.client.dto.AuthenticationResponse;
import xyz.kristo.projectx.smartid.dao.SmartIdDao;
import xyz.kristo.projectx.smartid.exception.BusinessException;
import xyz.kristo.projectx.smartid.model.SmartIdAuthenticationMethod;
import xyz.kristo.projectx.smartid.model.SmartIdDoLoginRequest;
import xyz.kristo.projectx.smartid.model.SmartIdDoRegisterRequest;
import xyz.kristo.projectx.smartid.model.SmartIdInitResponse;
import xyz.kristo.projectx.smartid.model.SmartIdInitLoginRequest;
import xyz.kristo.projectx.smartid.model.SmartIdInitRegisterRequest;

import java.util.Arrays;

@Service
@Transactional
@Slf4j
public class SmartIdService {
    private final SmartIdDao smartIdDao;
    private final AccountClient accountClient;
    private final AuthClient authClient;
    private final SmartIdClient smartIdClient;
    private final SmartIdRestConnector smartIdRestConnector;
    private final AuthenticationResponseValidator responseValidator;
    private final String certificateLevel;

    public SmartIdService(SmartIdDao smartIdDao,
                          AccountClient accountClient,
                          AuthClient authClient,
                          SmartIdClient smartIdClient,
                          SmartIdRestConnector smartIdRestConnector,
                          AuthenticationResponseValidator responseValidator,
                          @Value("${smartId.certificateLevel}") String certificateLevel) {
        this.smartIdDao = smartIdDao;
        this.accountClient = accountClient;
        this.authClient = authClient;
        this.smartIdClient = smartIdClient;
        this.smartIdRestConnector = smartIdRestConnector;
        this.responseValidator = responseValidator;
        this.certificateLevel = certificateLevel;
    }

    public SmartIdInitResponse initLogin(SmartIdInitLoginRequest request) {
        return initAuthentication(request.getCountryCode(), request.getIdentityCode());
    }

    public AuthenticationResponse doLogin(SmartIdDoLoginRequest request) {
        AuthenticationIdentity identity = doAuthentication(request.getSessionId(), request.getAuthenticationHash());
        SmartIdAuthenticationMethod method = smartIdDao.findSmartIdAuthenticationMethod(
                new SmartIdAuthenticationMethod(identity.getCountry(), identity.getIdentityCode()));

        if (method == null) {
            throw new BusinessException("Your Smart-ID account is not linked to any account!");
        }

        Account account = accountClient.findAccountById(method.getAccountId());

        if (account == null) {
            throw new BusinessException("Account not found!");
        }

        return authenticate(account);
    }

    public SmartIdInitResponse initRegister(SmartIdInitRegisterRequest request) {
        accountClient.validateAccountData(new CreateAccountRequest(request.getUsername(), request.getEmail()));
        return initAuthentication(request.getCountryCode(), request.getIdentityCode());
    }

    public AuthenticationResponse doRegister(SmartIdDoRegisterRequest request) {
        AuthenticationIdentity identity = doAuthentication(request.getSessionId(), request.getAuthenticationHash());
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(request.getUsername(), request.getEmail());

        accountClient.validateAccountData(createAccountRequest);

        SmartIdAuthenticationMethod method = smartIdDao.findSmartIdAuthenticationMethod(
                new SmartIdAuthenticationMethod(identity.getCountry(), identity.getIdentityCode()));

        if (method != null) {
            throw new BusinessException("Your Smart-ID account is already linked to another account!");
        }

        Account account = accountClient.createAccount(createAccountRequest);

        smartIdDao.createSmartIdAuthenticationMethod(
                account.getAccountId(), new SmartIdAuthenticationMethod(identity.getCountry(), identity.getIdentityCode()));

        return authenticate(account);
    }

    private SmartIdInitResponse initAuthentication(String countryCode, String identityCode) {
        try {
            SemanticsIdentifier semanticsIdentifier = new SemanticsIdentifier(
                    SemanticsIdentifier.IdentityType.PNO,
                    SemanticsIdentifier.CountryCode.valueOf(countryCode),
                    identityCode
            );

            AuthenticationHash authenticationHash = AuthenticationHash.generateRandomHash();
            String verificationCode = authenticationHash.calculateVerificationCode();

            AuthenticationRequestBuilder requestBuilder = smartIdClient.createAuthentication()
                    .withSemanticsIdentifier(semanticsIdentifier)
                    .withAuthenticationHash(authenticationHash)
                    .withCertificateLevel(certificateLevel)
                    .withAllowedInteractionsOrder(Arrays.asList(
                            Interaction.confirmationMessageAndVerificationCodeChoice("Long text (up to 200 characters) goes here."),
                            Interaction.verificationCodeChoice("Shorter text for less capable devices"),
                            Interaction.displayTextAndPIN("Shorter text for less capable devices")
                    ));

            String sessionId = requestBuilder.initiateAuthentication();

            return new SmartIdInitResponse(verificationCode, sessionId, authenticationHash.getHashInBase64());
        } catch (UserAccountNotFoundException e) {
            throw new BusinessException("Smart-ID account not found!");
        } catch (ServerMaintenanceException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Smart-ID authentication failed!");
        }
    }

    private AuthenticationIdentity doAuthentication(String sessionId, String authenticationHash) {
        AuthenticationHash hash = new AuthenticationHash();
        hash.setHashInBase64(authenticationHash);
        hash.setHashType(HashType.SHA512);

        AuthenticationRequestBuilder requestBuilder = smartIdClient.createAuthentication()
                .withAuthenticationHash(hash)
                .withCertificateLevel(certificateLevel);

        try {
            SessionStatus sessionStatus = smartIdRestConnector.getSessionStatus(sessionId);
            SmartIdAuthenticationResponse response = requestBuilder.createSmartIdAuthenticationResponse(sessionStatus);
            return responseValidator.validate(response);
        } catch (UserRefusedException e) {
            throw new BusinessException("User refused Smart-ID authentication from their phone.");
        } catch (SessionTimeoutException e) {
            throw new BusinessException("The Smart-ID authentication session timed out.");
        } catch (DocumentUnusableException e) {
            throw new BusinessException("The document associated with the Smart-ID account is unusable.");
        } catch (SmartIdException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Smart-ID authentication failed!");
        }
    }

    private AuthenticationResponse authenticate(Account account) {
        return authClient.authenticate(
                new AuthenticationRequest(account.getAccountId(), account.getUsername(), account.getEmail())
        );
    }
}
