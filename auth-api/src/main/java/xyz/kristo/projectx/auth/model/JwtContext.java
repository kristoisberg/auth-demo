package xyz.kristo.projectx.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public final class JwtContext {
    private Long accountId;
    private String username;
    private String email;
    private LocalDateTime expiryTime;
}
