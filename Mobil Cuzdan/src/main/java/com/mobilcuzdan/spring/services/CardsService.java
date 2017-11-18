package com.mobilcuzdan.spring.services;

import com.mobilcuzdan.spring.db.CardsDao;
import com.mobilcuzdan.spring.db.TransactionsDao;
import com.mobilcuzdan.spring.mvc.models.*;
import com.mobilcuzdan.spring.spring.ActiveUserId;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by fikricanca on 24.12.2016.
 */
@Component
public class CardsService {

    @Autowired
    CardsDao cardsDao;

    @Autowired
    TransactionsDao transactionsDao;


    public ActionResult addCard (Integer userId, String holderName, String cardNo, String cardName, String image) {

        Card card = new Card();
        card.setHolderName(holderName);
        card.setCardNo(cardNo);
        card.setCardName(cardName);
        card.setImage(image);

        Integer newCardId = cardsDao.addCard(userId, card);

        if (newCardId > 0) {
            card.setId(newCardId);
            return new ActionResult(true, "Kart eklendi.");
        }else {
            return new ActionResult(false, "Kart eklenemedi.");
        }

    }

    public List<Card> getUserCards (Integer userId) {

       return cardsDao.getUserCards(userId);

    }

    public ActionResult deleteCard (Integer cardId) {

        if (cardsDao.deleteCard(cardId) > 0) {

            return new ActionResult(true, "Kart başarıyla silindi.");

        }else {

            return new ActionResult(false, "Kart silinirken bir sorun oluştu. Lütfen tekrar deneyiniz.");

        }

    }

    public ActionResult updateCard(Card card) {

        if (cardsDao.updateCard(card) > 0) {

            return new ActionResult(true, "Kart bilgileri başarıyla güncellendi.");

        }else {

            return new ActionResult(false, "Kart bilgilerini güncellerken bir sorun oluştu. Lütfen tekrar deneyiniz.");

        }

    }

    public ActionResult addAmountToBalance (Integer userId, Double amount, Integer cardId) {

        Transaction transaction = new Transaction();
        transaction.setDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
        transaction.setUserId(userId);
        transaction.setCardId(cardId);
        transaction.setStoreId(0);
        transaction.setStoreName("");
        transaction.setAmount(amount);
        transaction.setType(2);

        if (cardsDao.addAmountToBalance(amount, cardId) > 0 && transactionsDao.addAmountToCard(transaction) > 0) {

            return new ActionResult(true, "Bakiye başarıyla eklendi.");

        } else {

            return new ActionResult(false, "Bakiye yüklemesi sırasında bir hata oluştu, lütfen tekrar deneyiniz.");

        }
    }

    public Double getBalanceOfCardById(Integer cardId) {

        return cardsDao.getBalance(cardId);

    }

    public Card getCardById(Integer cardId) {

        return cardsDao.getCardById(cardId);
    }

    public CardTransactionsResponse getRecentTransactions(Integer cardId) {

        CardTransactionsResponse cardTransactionsResponse = new CardTransactionsResponse();
        cardTransactionsResponse.setCard(cardsDao.getCardById(cardId));
        cardTransactionsResponse.setRecentTransactions(cardsDao.getRecentTransactions(cardId));
        return cardTransactionsResponse;

    }

}
