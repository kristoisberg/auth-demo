package xyz.kristo.projectx.password.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PasswordRegisterRequest {
    private String username;
    private String email;

    @NotNull
    @Size(min = 10)
    private String password;
}
