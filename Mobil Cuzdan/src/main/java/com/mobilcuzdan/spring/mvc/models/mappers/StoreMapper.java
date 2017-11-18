package com.mobilcuzdan.spring.mvc.models.mappers;

import com.mobilcuzdan.spring.mvc.models.Store;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by fikricanca on 31.12.2016.
 */
public class StoreMapper implements ResultSetMapper<Store> {

    public Store map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        Store store = new Store();
        store.setId(r.getInt("id"));
        store.setName(r.getString("name"));
        store.setEmail(r.getString("email"));
        store.setPhone(r.getString("phone"));
        store.setPassword(r.getString("password"));
        store.setCreditBalance(r.getFloat("creditBalance"));
        return store;

    }
}
