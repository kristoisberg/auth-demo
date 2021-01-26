package xyz.kristo.projectx.account.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private Long accountId;
    private String username;
    private String email;

    public Account(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
