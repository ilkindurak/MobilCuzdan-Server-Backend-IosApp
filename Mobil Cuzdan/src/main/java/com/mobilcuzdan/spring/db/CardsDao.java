package com.mobilcuzdan.spring.db;

import com.mobilcuzdan.spring.mvc.models.*;
import com.mobilcuzdan.spring.mvc.models.Transaction;
import com.mobilcuzdan.spring.mvc.models.mappers.CardMapper;
import com.mobilcuzdan.spring.mvc.models.mappers.TransactionMapper;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * Created by fikricanca on 24.12.2016.
 */
public abstract class CardsDao {

    @SqlUpdate("INSERT INTO cards (userId, holderName, cardNo, cardName, image) VALUES (:userId, :card.holderName, :card.cardNo, :card.cardName, :card.image)")
    @GetGeneratedKeys
    public abstract Integer addCard(@Bind("userId") Integer userId, @BindBean("card") Card card);

    @SqlQuery("SELECT * FROM cards JOIN users_cards ON users_cards.cardId = cards.id WHERE users_cards.userId=:userId")
    @Mapper(CardMapper.class)
    public abstract List<Card> getUserCards(@Bind("userId") Integer userId);

    @SqlUpdate("DELETE FROM cards WHERE cards.id = :cardId")
    public abstract Integer deleteCard(@Bind("cardId") Integer cardId);

    @SqlUpdate ("UPDATE cards SET holderName = COALESCE(:card.holderName, holderName), " + "cardNo = COALESCE(:card.cardNo, cardNo), " + "cardName = COALESCE(:card.cardName, cardName) " + "WHERE id = :card.id")
    public abstract Integer updateCard(@BindBean("card") Card card);

    @SqlUpdate("UPDATE cards SET balance = balance + :amount WHERE cards.id = :cardId")
    public abstract Integer addAmountToBalance(@Bind("amount") Double amount, @Bind("cardId") Integer cardId);

    @SqlUpdate("UPDATE cards SET balance = balance - :amount WHERE cards.id = :cardId")
    public abstract Integer substractAmountFromBalance(@Bind("amount") Double amount, @Bind("cardId") Integer cardId);

    @SqlQuery("SELECT balance FROM cards WHERE cards.id = :cardId")
    public abstract Double getBalance(@Bind("cardId") Integer cardId);

    @SqlQuery("SELECT * FROM cards WHERE cards.id = :cardId")
    @Mapper(CardMapper.class)
    public abstract Card getCardById(@Bind("cardId") Integer cardId);

    @SqlQuery("SELECT * FROM transactions WHERE transactions.cardId = :cardId ORDER BY id DESC LIMIT 5")
    @Mapper(TransactionMapper.class)
    public abstract List<Transaction> getRecentTransactions(@Bind("cardId") Integer cardId);
}