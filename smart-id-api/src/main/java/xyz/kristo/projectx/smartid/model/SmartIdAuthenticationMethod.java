package xyz.kristo.projectx.smartid.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SmartIdAuthenticationMethod {
    private Long accountId;
    private String countryCode;
    private String identityCode;

    public SmartIdAuthenticationMethod(String countryCode, String identityCode) {
        this.countryCode = countryCode;
        this.identityCode = identityCode;
    }
}
