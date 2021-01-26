package xyz.kristo.projectx.smartid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.kristo.projectx.smartid.model.AuthenticationResponse;
import xyz.kristo.projectx.smartid.model.SmartIdDoLoginRequest;
import xyz.kristo.projectx.smartid.model.SmartIdDoRegisterRequest;
import xyz.kristo.projectx.smartid.model.SmartIdInitResponse;
import xyz.kristo.projectx.smartid.model.SmartIdInitLoginRequest;
import xyz.kristo.projectx.smartid.model.SmartIdInitRegisterRequest;
import xyz.kristo.projectx.smartid.service.SmartIdService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SmartIdController {
    private final SmartIdService smartIdService;

    @PostMapping("login/init")
    public SmartIdInitResponse initLogin(@RequestBody @Valid SmartIdInitLoginRequest request) {
        return smartIdService.initLogin(request);
    }

    @PostMapping("login/do")
    public AuthenticationResponse doLogin(@RequestBody @Valid SmartIdDoLoginRequest request) {
        return smartIdService.doLogin(request);
    }

    @PostMapping("register/init")
    public SmartIdInitResponse initRegister(@RequestBody @Valid SmartIdInitRegisterRequest request) {
        return smartIdService.initRegister(request);
    }

    @PostMapping("register/do")
    public AuthenticationResponse doRegister(@RequestBody @Valid SmartIdDoRegisterRequest request) {
        return smartIdService.doRegister(request);
    }
}
