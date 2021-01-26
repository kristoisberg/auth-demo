package xyz.kristo.projectx.account.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CreateAccountRequest {
    @NotNull
    @Size(min = 3, max = 32)
    private String username;

    @NotNull
    @Email
    @Size(max = 128)
    private String email;
}
