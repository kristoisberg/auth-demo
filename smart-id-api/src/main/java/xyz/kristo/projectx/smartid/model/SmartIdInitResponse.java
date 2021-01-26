package xyz.kristo.projectx.smartid.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmartIdInitResponse {
    private final String verificationCode;
    private final String sessionId;
    private final String authenticationHash;
}
