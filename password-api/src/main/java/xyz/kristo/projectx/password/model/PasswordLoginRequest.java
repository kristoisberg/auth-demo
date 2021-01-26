package xyz.kristo.projectx.password.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PasswordLoginRequest {
    private String usernameOrEmail;

    @NotNull
    @NotEmpty
    private String password;
}
