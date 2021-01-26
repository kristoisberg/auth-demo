package xyz.kristo.projectx.password.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.kristo.projectx.password.model.PasswordAuthenticationMethod;

@Repository
public interface PasswordDao {
    void createPasswordAuthenticationMethod(@Param("accountId") Long accountId,
                                            @Param("method") PasswordAuthenticationMethod method);

    PasswordAuthenticationMethod findPasswordAuthenticationMethod(@Param("accountId") Long accountId);
}
