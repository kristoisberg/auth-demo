package xyz.kristo.projectx.smartid.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SmartIdDoLoginRequest {
    @NotNull
    @NotEmpty
    private String sessionId;

    @NotNull
    @NotEmpty
    private String authenticationHash;
}
