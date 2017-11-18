package com.mobilcuzdan.spring.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by fikricanc on 18.12.2016.
 */
public abstract class SecurityDao {

    @SqlUpdate("INSERT INTO mobile_auth_tokens (userId, token) VALUES (#userId, #token) ON DUPLICATE KEY UPDATE token = #token")
    public abstract void createTokenByUserId(@Bind("userId") Integer userId, @Bind("token") String token);

    @SqlUpdate("INSERT INTO passwords (userId, password) VALUES (#userId, #password) ON DUPLICATE KEY UPDATE password = #password")
    public abstract void createUserPasswordByUserId(@Bind("userId") Integer userId, @Bind("password") String passwordCrypt);

    @SqlUpdate("UPDATE mobile_auth_tokens SET token = COALESCE(#token, token) WHERE userId = #userId")
    public abstract void updateTokenByUserId(@Bind("userId") Integer userId, @Bind("token") String token);

    @SqlUpdate("UPDATE passwords SET password = #passwordCrypt WHERE userId = #userId")
    public abstract void updateUserPasswordByUserId(@Bind("userId") Integer userId, @Bind("passwordCrypt") String passwordCrypt);

    @SqlQuery("SELECT password FROM passwords WHERE userId = #userId")
    public abstract String getPasswordByUserId(@Bind("userId") Integer userId);

    @SqlQuery("SELECT userId FROM mobile_auth_tokens WHERE token=#token")
    public abstract Integer getUserIdForToken(@Bind("token") String token);



}
