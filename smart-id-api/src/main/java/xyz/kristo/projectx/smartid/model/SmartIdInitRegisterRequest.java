package xyz.kristo.projectx.smartid.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SmartIdInitRegisterRequest {
    private String username;
    private String email;

    @NotNull
    @NotEmpty
    private String countryCode;

    @NotNull
    @NotEmpty
    private String identityCode;
}
