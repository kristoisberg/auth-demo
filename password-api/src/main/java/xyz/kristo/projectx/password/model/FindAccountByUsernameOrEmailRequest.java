package xyz.kristo.projectx.password.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class FindAccountByUsernameOrEmailRequest {
    @NotNull
    @NotEmpty
    private final String usernameOrEmail;
}
