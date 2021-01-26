package xyz.kristo.projectx.mobileid.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.kristo.projectx.mobileid.model.AuthenticationRequest;
import xyz.kristo.projectx.mobileid.model.AuthenticationResponse;
import xyz.kristo.projectx.mobileid.model.VerificationRequest;

import javax.validation.Valid;

@Service
public class AuthService {
    private final RestTemplate restTemplate;
    private final String serviceUrl;

    public AuthService(RestTemplate restTemplate, @Value("${services.auth}") String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {
        return restTemplate.postForObject(serviceUrl + "/authenticate", request, AuthenticationResponse.class);
    }

    public void verify(@Valid VerificationRequest request) {
        restTemplate.postForLocation(serviceUrl + "/verify", request);
    }
}
