package xyz.kristo.projectx.account.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static xyz.kristo.projectx.account.util.JdbcUrlConverter.convert;

public class JdbcUriConverterTest {
    @Test
    public void shouldConvertDokkuDatabaseUrl() {
        assertThat(convert("postgres://lolipop:SOME_PASSWORD@dokku-postgres-lolipop:5432/lolipop"))
                .isEqualTo("jdbc:postgresql://dokku-postgres-lolipop:5432/lolipop?user=lolipop&password=SOME_PASSWORD");
    }
}
