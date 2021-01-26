package xyz.kristo.projectx.account.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class FindAccountByIdRequest {
    @NotNull
    private Long accountId;
}
