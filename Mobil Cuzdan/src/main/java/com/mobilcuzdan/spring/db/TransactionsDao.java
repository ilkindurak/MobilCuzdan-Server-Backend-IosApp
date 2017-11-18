package com.mobilcuzdan.spring.db;

import com.mobilcuzdan.spring.mvc.models.Transaction;
import org.skife.jdbi.v2.sqlobject.*;

/**
 * Created by fikricanca on 31.12.2016.
 */
public abstract class TransactionsDao {

    @SqlUpdate("INSERT INTO transactions (date, userId, cardId, storeId, storeName, amount, type) VALUES (:transaction.date, :transaction.userId, :transaction.cardId, :transaction.storeId, :transaction.storeName, :transaction.amount, :transaction.type)")
    @GetGeneratedKeys
    public abstract Integer makePaymentWithCard(@BindBean("transaction")Transaction transaction);

    @SqlUpdate("INSERT INTO transactions (date, userId, cardId, storeId, storeName, amount, type) VALUES (:transaction.date, :transaction.userId, :transaction.cardId, :transaction.storeId, :transaction.storeName, :transaction.amount, :transaction.type)")
    @GetGeneratedKeys
    public abstract Integer addAmountToCard(@BindBean("transaction")Transaction transaction);

    @SqlUpdate("UPDATE users_points SET points = points + :moneyPoint WHERE userId=:userId")
    public abstract void addMoneyPointToUser(@Bind("userId") Integer userId, @Bind("moneyPoint") Double moneyPoint);

    @SqlUpdate("UPDATE users_points SET points = points - :moneyPoint WHERE userId=:userId")
    public abstract void substractMoneyPointFromUser(@Bind("userId") Integer userId, @Bind("moneyPoint") Double moneyPoint);

}
