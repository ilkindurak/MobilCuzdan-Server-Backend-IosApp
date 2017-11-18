package com.mobilcuzdan.spring.factories;

import com.mobilcuzdan.spring.db.*;
import com.mobilcuzdan.spring.mvc.models.Transaction;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by YunusS on 3/27/2016.
 */

@Configuration
public class DbiDaoFactory {

    @Autowired
    private DBI dbi;

    @Bean
    public ContentDao getContentDao() {
        return dbi.onDemand(ContentDao.class);
    }

    @Bean
    public UsersDao getUsersDao() {
        return dbi.onDemand(UsersDao.class);
    }

    @Bean
    public CardsDao getCardsDao() { return dbi.onDemand(CardsDao.class); }

    @Bean
    public SecurityDao getSecurityDao() {
        return dbi.onDemand(SecurityDao.class);
    }

    @Bean
    public StoresDao getStoresDao() { return  dbi.onDemand(StoresDao.class); }

    @Bean
    public TransactionsDao getTransactionsDao() {return  dbi.onDemand(TransactionsDao.class); }

}
