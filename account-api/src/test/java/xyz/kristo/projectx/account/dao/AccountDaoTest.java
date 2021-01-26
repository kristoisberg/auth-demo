package xyz.kristo.projectx.account.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

public class AccountDaoTest extends DaoTest {
    @Autowired
    private AccountDao accountDao;

    @Test
    public void shouldFindExistentUsername() {
        assertThat(accountDao.isUsernameInUse("test")).isTrue();
    }

    @Test
    public void shouldNotFindNonExistentUsername() {
        assertThat(accountDao.isUsernameInUse("asd")).isFalse();
    }
}
