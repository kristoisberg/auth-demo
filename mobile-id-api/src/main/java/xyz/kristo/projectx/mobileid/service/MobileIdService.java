package xyz.kristo.projectx.mobileid.service;

import ee.sk.mid.MidAuthentication;
import ee.sk.mid.MidAuthenticationHashToSign;
import ee.sk.mid.MidAuthenticationIdentity;
import ee.sk.mid.MidAuthenticationResponseValidator;
import ee.sk.mid.MidAuthenticationResult;
import ee.sk.mid.MidClient;
import ee.sk.mid.MidDisplayTextFormat;
import ee.sk.mid.MidHashType;
import ee.sk.mid.MidLanguage;
import ee.sk.mid.exception.MidDeliveryException;
import ee.sk.mid.exception.MidInternalErrorException;
import ee.sk.mid.exception.MidInvalidUserConfigurationException;
import ee.sk.mid.exception.MidMissingOrInvalidParameterException;
import ee.sk.mid.exception.MidNotMidClientException;
import ee.sk.mid.exception.MidPhoneNotAvailableException;
import ee.sk.mid.exception.MidSessionNotFoundException;
import ee.sk.mid.exception.MidSessionTimeoutException;
import ee.sk.mid.exception.MidUnauthorizedException;
import ee.sk.mid.exception.MidUserCancellationException;
import ee.sk.mid.rest.dao.MidSessionStatus;
import ee.sk.mid.rest.dao.request.MidAuthenticationRequest;
import ee.sk.mid.rest.dao.response.MidAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kristo.projectx.account.client.AccountClient;
import xyz.kristo.projectx.account.client.dto.Account;
import xyz.kristo.projectx.account.client.dto.CreateAccountRequest;
import xyz.kristo.projectx.auth.client.AuthClient;
import xyz.kristo.projectx.auth.client.dto.AuthenticationRequest;
import xyz.kristo.projectx.auth.client.dto.AuthenticationResponse;
import xyz.kristo.projectx.mobileid.dao.MobileIdDao;
import xyz.kristo.projectx.mobileid.exception.BusinessException;
import xyz.kristo.projectx.mobileid.model.MobileIdAuthenticationMethod;
import xyz.kristo.projectx.mobileid.model.MobileIdDoLoginRequest;
import xyz.kristo.projectx.mobileid.model.MobileIdDoRegisterRequest;
import xyz.kristo.projectx.mobileid.model.MobileIdInitResponse;
import xyz.kristo.projectx.mobileid.model.MobileIdInitLoginRequest;
import xyz.kristo.projectx.mobileid.model.MobileIdInitRegisterRequest;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MobileIdService {
    private final MobileIdDao mobileIdDao;
    private final AccountClient accountClient;
    private final AuthClient authClient;
    private final MidClient mobileIdClient;
    private final MidAuthenticationResponseValidator responseValidator;

    public MobileIdInitResponse initLogin(MobileIdInitLoginRequest request) {
        return initAuthentication(request.getPhoneNumber(), request.getIdentityCode());
    }

    public AuthenticationResponse doLogin(MobileIdDoLoginRequest request) {
        MidAuthenticationIdentity identity = doAuthentication(request.getSessionId(), request.getAuthenticationHash());
        MobileIdAuthenticationMethod method = mobileIdDao.findMobileIdAuthenticationMethod(
                new MobileIdAuthenticationMethod(identity.getCountry(), identity.getIdentityCode()));

        if (method == null) {
            throw new BusinessException("Your Mobile-ID account is not linked to any account!");
        }

        Account account = accountClient.findAccountById(method.getAccountId());

        if (account == null) {
            throw new BusinessException("Account not found!");
        }

        return authenticate(account);
    }

    public MobileIdInitResponse initRegister(MobileIdInitRegisterRequest request) {
        accountClient.validateAccountData(new CreateAccountRequest(request.getUsername(), request.getEmail()));
        return initAuthentication(request.getPhoneNumber(), request.getIdentityCode());
    }

    public AuthenticationResponse doRegister(MobileIdDoRegisterRequest request) {
        MidAuthenticationIdentity identity = doAuthentication(request.getSessionId(), request.getAuthenticationHash());
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(request.getUsername(), request.getEmail());

        accountClient.validateAccountData(createAccountRequest);

        MobileIdAuthenticationMethod method = mobileIdDao.findMobileIdAuthenticationMethod(
                new MobileIdAuthenticationMethod(identity.getCountry(), identity.getIdentityCode()));

        if (method != null) {
            throw new BusinessException("Your Mobile-ID account is already linked to another account!");
        }

        Account account = accountClient.createAccount(createAccountRequest);

        mobileIdDao.createMobileIdAuthenticationMethod(
                account.getAccountId(), new MobileIdAuthenticationMethod(identity.getCountry(), identity.getIdentityCode()));

        return authenticate(account);
    }

    private MobileIdInitResponse initAuthentication(String phoneNumber, String identityCode) {
        try {
            MidAuthenticationHashToSign authenticationHash = MidAuthenticationHashToSign.generateRandomHashOfType(MidHashType.SHA256);

            String verificationCode = authenticationHash.calculateVerificationCode();

            MidAuthenticationRequest request = MidAuthenticationRequest.newBuilder()
                    .withPhoneNumber(phoneNumber)
                    .withNationalIdentityNumber(identityCode)
                    .withHashToSign(authenticationHash)
                    .withLanguage(MidLanguage.ENG)
                    .withDisplayText("")
                    .withDisplayTextFormat(MidDisplayTextFormat.GSM7)
                    .build();

            MidAuthenticationResponse response = mobileIdClient.getMobileIdConnector().authenticate(request);

            return new MobileIdInitResponse(verificationCode, response.getSessionID(), authenticationHash.getHashInBase64());
        } catch (MidInternalErrorException | MidMissingOrInvalidParameterException | MidUnauthorizedException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Mobile-ID authentication failed!");
        }
    }

    private MidAuthenticationIdentity doAuthentication(String sessionId, String authenticationHash) {
        MidAuthenticationHashToSign hash = MidAuthenticationHashToSign.newBuilder()
                .withHashType(MidHashType.SHA256)
                .withHashInBase64(authenticationHash)
                .build();

        try {
            MidSessionStatus sessionStatus =
                    mobileIdClient.getSessionStatusPoller().fetchFinalAuthenticationSessionStatus(sessionId);
            MidAuthentication authentication = mobileIdClient.createMobileIdAuthentication(sessionStatus, hash);
            MidAuthenticationResult result = responseValidator.validate(authentication);

            if (!result.isValid()) {
                throw new BusinessException(String.join(" ", result.getErrors()));
            }

            return result.getAuthenticationIdentity();
        } catch (MidUserCancellationException e) {
            throw new BusinessException("User cancelled operation from their phone.");
        } catch (MidNotMidClientException e) {
            throw new BusinessException("User is not a MID client or user's certificates are revoked.");
        } catch (MidSessionTimeoutException e) {
            throw new BusinessException("User did not type in PIN code or communication error.");
        } catch (MidPhoneNotAvailableException e) {
            throw new BusinessException("Unable to reach phone/SIM card. User needs to check if phone has coverage.");
        } catch (MidDeliveryException e) {
            throw new BusinessException("Error communicating with the phone/SIM card.");
        } catch (MidInvalidUserConfigurationException e) {
            throw new BusinessException("Mobile-ID configuration on user's SIM card differs from what is configured " +
                    "on service provider's side. User needs to contact their mobile operator.");
        } catch (MidSessionNotFoundException | MidMissingOrInvalidParameterException | MidUnauthorizedException |
                MidInternalErrorException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Mobile-ID authentication failed!");
        }
    }

    private AuthenticationResponse authenticate(Account account) {
        return authClient.authenticate(
                new AuthenticationRequest(account.getAccountId(), account.getUsername(), account.getEmail())
        );
    }
}
