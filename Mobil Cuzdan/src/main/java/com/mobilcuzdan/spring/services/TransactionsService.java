package com.mobilcuzdan.spring.services;

import com.mobilcuzdan.spring.db.CardsDao;
import com.mobilcuzdan.spring.db.StoresDao;
import com.mobilcuzdan.spring.db.TransactionsDao;
import com.mobilcuzdan.spring.mvc.models.ActionResult;
import com.mobilcuzdan.spring.mvc.models.Store;
import com.mobilcuzdan.spring.mvc.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fikricanca on 31.12.2016.
 */

@Component
public class TransactionsService {

    @Autowired
    TransactionsDao transactionsDao;

    @Autowired
    CardsDao cardsDao;

    @Autowired
    StoresDao storesDao;



    public ActionResult makePaymentWithCard(int userId, int cardId, int storeId, Double amount) {

        Transaction transaction = new Transaction();
        transaction.setDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
        transaction.setUserId(userId);
        transaction.setCardId(cardId);
        transaction.setStoreId(storeId);
        transaction.setAmount(amount);
        transaction.setType(1);
        Store store = storesDao.getStoreById(storeId);
        transaction.setStoreName(store.getName());

        if (amount >= 100) {
            addMoneyPointsToUser(userId, amount/10);
        }

        if (amount > cardsDao.getBalance(cardId)) {
            return new ActionResult(false, "Kartınızda yeterli bakiye bulunmamaktadır.");
        }else {
            Integer newTransactionId = transactionsDao.makePaymentWithCard(transaction);

            if (newTransactionId > 0 && cardsDao.substractAmountFromBalance(amount, cardId) > 0 && storesDao.addAmountToStoreBalance(amount, storeId) > 0) {
                transaction.setId(newTransactionId);
                return new ActionResult(true, "Ödeme işlemi başarıyla gerçekleştirildi.");
            }else {
                return new ActionResult(false,"Ödeme işlemi sırasında bir hata oluştu. Lütfen tekrar deneyiniz.");
            }
        }

    }

    public void addMoneyPointsToUser(Integer userId, Double moneyPoint) {

        transactionsDao.addMoneyPointToUser(userId, moneyPoint);

    }

    public void substractMoneyPointFromUser(Integer userId, Double moneyPoint) {

        transactionsDao.substractMoneyPointFromUser(userId, moneyPoint);

    }

}
