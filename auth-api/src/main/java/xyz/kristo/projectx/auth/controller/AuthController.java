package xyz.kristo.projectx.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.kristo.projectx.auth.model.AuthenticationRequest;
import xyz.kristo.projectx.auth.model.AuthenticationResponse;
import xyz.kristo.projectx.auth.model.VerificationRequest;
import xyz.kristo.projectx.auth.service.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("authenticate")
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("verify")
    public void verify(@RequestBody @Valid VerificationRequest request) {
        authService.verify(request);
    }
}
