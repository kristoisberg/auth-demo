package xyz.kristo.projectx.mobileid.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class FindAccountByIdRequest {
    @NotNull
    @NotEmpty
    private final Long accountId;
}
