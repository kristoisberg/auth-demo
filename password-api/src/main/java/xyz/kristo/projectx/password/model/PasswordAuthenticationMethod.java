package xyz.kristo.projectx.password.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordAuthenticationMethod {
    private Long accountId;
    private String passwordHash;

    public PasswordAuthenticationMethod(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
