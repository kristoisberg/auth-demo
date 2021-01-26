package xyz.kristo.projectx.mobileid.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.kristo.projectx.mobileid.model.MobileIdAuthenticationMethod;

@Repository
public interface MobileIdDao {
    void createMobileIdAuthenticationMethod(@Param("accountId") Long accountId,
                                            @Param("method") MobileIdAuthenticationMethod method);

    MobileIdAuthenticationMethod findMobileIdAuthenticationMethod(@Param("method") MobileIdAuthenticationMethod method);
}
