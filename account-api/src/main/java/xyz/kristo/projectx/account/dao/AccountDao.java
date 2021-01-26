package xyz.kristo.projectx.account.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.kristo.projectx.account.model.Account;

@Repository
public interface AccountDao {
    void createAccount(@Param("account") Account account);

    boolean isUsernameInUse(@Param("username") String username);

    boolean isEmailInUse(@Param("email") String email);

    Account findAccountByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    Account findAccountById(@Param("accountId") Long accountId);
}
