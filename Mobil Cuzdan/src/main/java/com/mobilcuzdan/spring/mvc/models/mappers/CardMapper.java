package com.mobilcuzdan.spring.mvc.models.mappers;

import com.mobilcuzdan.spring.mvc.models.Card;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fikricanca on 24.12.2016.
 */
public class CardMapper implements ResultSetMapper<Card> {
    public Card map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Card card = new Card();
        card.setId(r.getInt("id"));
        card.setHolderName(r.getString("holderName"));
        card.setCardNo(r.getString("cardNo"));
        card.setCardName(r.getString("cardName"));
        card.setImage(r.getString("image"));
        card.setBalance(r.getDouble("balance"));
        return card;
    }
}
