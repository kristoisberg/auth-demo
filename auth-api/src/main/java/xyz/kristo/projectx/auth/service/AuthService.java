package xyz.kristo.projectx.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.kristo.projectx.auth.model.AuthenticationRequest;
import xyz.kristo.projectx.auth.model.AuthenticationResponse;
import xyz.kristo.projectx.auth.model.JwtContext;
import xyz.kristo.projectx.auth.model.VerificationRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        JwtContext context = jwtService.create(request.getAccountId(), request.getUsername(), request.getEmail());
        return new AuthenticationResponse(jwtService.encode(context));
    }

    public void verify(VerificationRequest request) {
        jwtService.verify(request.getJwt());
    }
}
