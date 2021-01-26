package xyz.kristo.projectx.auth.client;

import org.springframework.web.client.RestTemplate;
import xyz.kristo.projectx.auth.client.dto.AuthenticationRequest;
import xyz.kristo.projectx.auth.client.dto.AuthenticationResponse;
import xyz.kristo.projectx.auth.client.dto.VerificationRequest;
import xyz.kristo.projectx.common.rest.BaseRestClient;

import javax.validation.Validator;

public class AuthClient extends BaseRestClient {
    public AuthClient(RestTemplate restTemplate, Validator validator, String serviceUrl) {
        super(restTemplate, validator, serviceUrl);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return post("authenticate", request, AuthenticationResponse.class);
    }

    public void verify(VerificationRequest request) {
        post("verify", request);
    }
}
