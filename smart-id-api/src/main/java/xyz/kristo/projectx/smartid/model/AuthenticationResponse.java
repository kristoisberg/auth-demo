package xyz.kristo.projectx.smartid.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class AuthenticationResponse {
    private String jwt;
}
