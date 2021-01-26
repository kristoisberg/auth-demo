package xyz.kristo.projectx.smartid.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.kristo.projectx.smartid.model.SmartIdAuthenticationMethod;

@Repository
public interface SmartIdDao {
    void createSmartIdAuthenticationMethod(@Param("accountId") Long accountId,
                                           @Param("method") SmartIdAuthenticationMethod method);

    SmartIdAuthenticationMethod findSmartIdAuthenticationMethod(@Param("method") SmartIdAuthenticationMethod method);
}
