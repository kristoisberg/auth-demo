package xyz.kristo.projectx.mobileid.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MobileIdAuthenticationMethod {
    private Long accountId;
    private String countryCode;
    private String identityCode;

    public MobileIdAuthenticationMethod(String countryCode, String identityCode) {
        this.countryCode = countryCode;
        this.identityCode = identityCode;
    }
}
