package xyz.kristo.projectx.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class VerificationRequest {
    @NotNull
    @NotEmpty
    private String jwt;
}
