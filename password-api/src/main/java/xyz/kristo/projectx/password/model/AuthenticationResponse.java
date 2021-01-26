package xyz.kristo.projectx.password.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class AuthenticationResponse {
    private String jwt;
}
