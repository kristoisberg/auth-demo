package xyz.kristo.projectx.mobileid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.kristo.projectx.mobileid.model.AuthenticationResponse;
import xyz.kristo.projectx.mobileid.model.MobileIdDoLoginRequest;
import xyz.kristo.projectx.mobileid.model.MobileIdDoRegisterRequest;
import xyz.kristo.projectx.mobileid.model.MobileIdInitResponse;
import xyz.kristo.projectx.mobileid.model.MobileIdInitLoginRequest;
import xyz.kristo.projectx.mobileid.model.MobileIdInitRegisterRequest;
import xyz.kristo.projectx.mobileid.service.MobileIdService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MobileIdController {
    private final MobileIdService mobileIdService;

    @PostMapping("login/init")
    public MobileIdInitResponse initLogin(@RequestBody @Valid MobileIdInitLoginRequest request) {
        return mobileIdService.initLogin(request);
    }

    @PostMapping("login/do")
    public AuthenticationResponse doLogin(@RequestBody @Valid MobileIdDoLoginRequest request) {
        return mobileIdService.doLogin(request);
    }

    @PostMapping("register/init")
    public MobileIdInitResponse initRegister(@RequestBody @Valid MobileIdInitRegisterRequest request) {
        return mobileIdService.initRegister(request);
    }

    @PostMapping("register/do")
    public AuthenticationResponse doRegister(@RequestBody @Valid MobileIdDoRegisterRequest request) {
        return mobileIdService.doRegister(request);
    }
}
