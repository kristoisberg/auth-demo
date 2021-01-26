package xyz.kristo.projectx.mobileid.model;

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
