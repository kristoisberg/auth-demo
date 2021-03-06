package xyz.kristo.projectx.account.client.dto;

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
}
