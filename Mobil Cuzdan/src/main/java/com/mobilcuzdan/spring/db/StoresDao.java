package com.mobilcuzdan.spring.db;

import com.mobilcuzdan.spring.mvc.models.Store;
import com.mobilcuzdan.spring.mvc.models.mappers.StoreMapper;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * Created by fikricanca on 31.12.2016.
 */
public abstract class StoresDao {

    @SqlUpdate("INSERT INTO stores (name, email, password, phone) VALUES (:store.name, :store.email, :store.password, :store.phone)")
    @GetGeneratedKeys
    public abstract Integer createStore(@BindBean("store") Store store);

    @SqlQuery("SELECT * FROM stores WHERE email=:email")
    @Mapper(StoreMapper.class)
    public abstract Store getUserByEmail(@Bind("email") String email);

    @SqlQuery("SELECT * FROM stores WHERE id=:id")
    @Mapper(StoreMapper.class)
    public abstract Store getStoreById(@Bind("id") Integer id);

    @SqlUpdate("UPDATE stores SET creditBalance = creditBalance + :amount WHERE stores.id = :storeId")
    public abstract Integer addAmountToStoreBalance(@Bind("amount") Double amount, @Bind("storeId") Integer storeId);
}
