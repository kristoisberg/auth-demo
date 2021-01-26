package xyz.kristo.projectx.smartid.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class FindAccountByIdRequest {
    @NotNull
    @NotEmpty
    private final Long accountId;
}
