package xyz.kristo.projectx.password.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.kristo.projectx.auth.client.dto.AuthenticationResponse;
import xyz.kristo.projectx.password.model.PasswordLoginRequest;
import xyz.kristo.projectx.password.model.PasswordRegisterRequest;
import xyz.kristo.projectx.password.service.PasswordService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("login")
    public AuthenticationResponse login(@RequestBody @Valid PasswordLoginRequest request) {
        return passwordService.login(request);
    }

    @PostMapping("register")
    public AuthenticationResponse register(@RequestBody @Valid PasswordRegisterRequest request) {
        return passwordService.register(request);
    }
}
