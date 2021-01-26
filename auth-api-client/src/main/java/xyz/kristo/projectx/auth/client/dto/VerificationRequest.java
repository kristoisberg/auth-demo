package xyz.kristo.projectx.auth.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class VerificationRequest {
    @NotNull
    private String jwt;
}
