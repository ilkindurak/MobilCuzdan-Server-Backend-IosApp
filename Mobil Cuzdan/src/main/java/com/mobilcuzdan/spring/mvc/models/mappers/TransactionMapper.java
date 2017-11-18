package com.mobilcuzdan.spring.mvc.models.mappers;

import com.mobilcuzdan.spring.mvc.models.Transaction;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fikricanca on 31.12.2016.
 */
public class TransactionMapper implements ResultSetMapper<Transaction> {

    public Transaction map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(r.getInt("id"));
        transaction.setDate(r.getString("date"));
        transaction.setUserId(r.getInt("userId"));
        transaction.setCardId(r.getInt("cardId"));
        transaction.setStoreId(r.getInt("storeId"));
        transaction.setStoreName(r.getString("storeName"));
        transaction.setAmount(r.getDouble("amount"));
        transaction.setType(r.getInt("type"));
        return transaction;
    }

}
