package xyz.kristo.projectx.password.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.kristo.projectx.password.model.AuthenticationRequest;
import xyz.kristo.projectx.password.model.AuthenticationResponse;
import xyz.kristo.projectx.password.model.VerificationRequest;

import javax.validation.Valid;
import javax.validation.Validator;

@Service
public class AuthService extends RestService {
    public AuthService(RestTemplate restTemplate, Validator validator, @Value("${services.auth}") String serviceUrl) {
        super(restTemplate, validator, serviceUrl);
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {
        return post("authenticate", request, AuthenticationResponse.class);
    }

    public void verify(@Valid VerificationRequest request) {
        post("verify", request);
    }
}
