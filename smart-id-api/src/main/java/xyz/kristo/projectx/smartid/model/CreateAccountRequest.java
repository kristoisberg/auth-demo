package xyz.kristo.projectx.smartid.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccountRequest {
    @NotNull
    @Size(min = 3, max = 32)
    private final String username;

    @NotNull
    @Email
    @Size(max = 128)
    private final String email;
}
