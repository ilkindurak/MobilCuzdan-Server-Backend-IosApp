package com.mobilcuzdan.spring.db;

import com.mobilcuzdan.spring.mvc.models.User;
import com.mobilcuzdan.spring.mvc.models.mappers.UserMapper;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.util.DoubleMapper;

import java.util.List;

/**
 * Created by fikricanca on 18.12.2016.
 */
public abstract class UsersDao {

    @SqlUpdate("INSERT INTO users (name, surname, email, password, tcNo, phone, image, fbId, fbAccessToken, token) VALUES (:user.name, :user.surname, :user.email, :user.password, :user.tcNo, :user.phone, :user.image, :user.fbId, :user.fbAccessToken, :token)")
    @GetGeneratedKeys
    public abstract Integer createUser(@BindBean("user") User user, @Bind("token") String token);

    @SqlUpdate("UPDATE users SET name = COALESCE(:user.name, name), " +
            "surname = COALESCE(:user.surname, surname), " +
            "email = COALESCE(:user.email, email), " +
            "password = COALESCE(:user.password, password), " +
            "tcNo = COALESCE(:user.tcNo, tcNo), " +
            "phone = COALESCE(:user.phone, phone), " +
            "image = COALESCE(:user.image, image), " +
            "fbId = COALESCE(:user.fbId, fbId), " +
            "fbAccessToken = COALESCE(:user.fbAccessToken, fbAccessToken) " +
            "WHERE id = :user.id")
    public abstract Integer updateUser(@BindBean("user") User user);

    @SqlQuery("SELECT * FROM users WHERE email=:email")
    @Mapper(UserMapper.class)
    public abstract User getUserByEmail(@Bind("email") String email);

    @SqlQuery("SELECT * FROM users")
    @Mapper(UserMapper.class)
    public abstract List<User> getAllUsers();

    @SqlUpdate("DELETE FROM users WHERE id=:id")
    public abstract void deleteUser(@Bind("id") Integer id);

    @SqlQuery("SELECT * FROM users WHERE id=:id")
    @Mapper(UserMapper.class)
    public abstract User getUserById(@Bind("id") Integer id);

    @SqlQuery("SELECT 1 FROM users WHERE id=:id AND password=:password")
    @Mapper(UserMapper.class)
    public abstract List<User> getUserWithIdAndPassword(@Bind("id") Integer userId, @Bind("password") String password);

    @SqlUpdate("UPDATE users SET password=:newPassword WHERE id=:id")
    public abstract void updateUserPassword(@Bind("id") Integer userId, @Bind("newPassword") String newPassword);

    @SqlQuery("SELECT * FROM users WHERE token=:token")
    @Mapper(UserMapper.class)
    public abstract User getUserByToken(@Bind("token") String token);

    @SqlUpdate("UPDATE users SET token = COALESCE(:token, token) WHERE id=:userId")
    public abstract Integer updateTokenByUserId(@Bind("userId") Integer userId, @Bind("token") String token);

    @SqlQuery("SELECT points FROM users_points WHERE userId=:userId")
    @Mapper(DoubleMapper.class)
    public abstract Double getUserPoints(@Bind("userId") Integer userId);

    @SqlUpdate("UPDATE users_points SET points = points + :amount WHERE userId IN (SELECT id FROM users WHERE phone = :phoneNumber)")
    public abstract Integer transferPoints(@Bind("amount") Double amount, @Bind("phoneNumber") String phoneNumber);

    @SqlUpdate("UPDATE users_points SET points = points - :amount WHERE userId = :userId")
    public abstract Integer substractPoints(@Bind("userId") Integer userId, @Bind("amount") Double amount);

    @SqlQuery("UPDATE users_points SET points = points + :amount WHERE userId = :userId")
    public abstract Integer addPoints(@Bind("userId") Integer userId, @Bind("amount") Double amount);

    @SqlUpdate("UPDATE users SET notificationId = COALESCE(:notificationId, notificationId) WHERE users.id = :userId")
    public abstract Integer updateNotificationIdForUser(@Bind("userId") Integer userId, @Bind("notificationId") String notificationId);

    @SqlQuery("SELECT notificationId FROM users WHERE phone = :phone ")
    public abstract String getUserNotificationIdByPhone(@Bind("phone") String phone);

}
