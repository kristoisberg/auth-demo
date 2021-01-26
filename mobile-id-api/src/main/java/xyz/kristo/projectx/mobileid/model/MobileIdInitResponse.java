package xyz.kristo.projectx.mobileid.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MobileIdInitResponse {
    private final String verificationCode;
    private final String sessionId;
    private final String authenticationHash;
}
