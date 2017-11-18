package com.mobilcuzdan.spring.mvc.controller;

/**
 * Created by fikricanca on 24.12.2016.
 */

import com.mobilcuzdan.spring.mvc.models.ActionResult;
import com.mobilcuzdan.spring.mvc.models.Card;
import com.mobilcuzdan.spring.mvc.models.CardTransactionsResponse;
import com.mobilcuzdan.spring.mvc.models.Transaction;
import com.mobilcuzdan.spring.services.CardsService;
import com.mobilcuzdan.spring.services.TransactionsService;
import com.mobilcuzdan.spring.spring.ActiveUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
public class CardsController {

    @Autowired
    CardsService cardsService;

    @Autowired
    TransactionsService transactionsService;

    @RequestMapping(value = "/add-card", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ActionResult addCard(@ActiveUserId Integer userId, @RequestParam String holderName, @RequestParam String cardNo, @RequestParam String cardName, @RequestParam(required = false, defaultValue = "") String image) {

            ActionResult actionResult = cardsService.addCard(userId, holderName, cardNo, cardName, image);
            return actionResult;

    }

    @RequestMapping(value = "/get-user-cards", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public List<Card> getUserCards(@ActiveUserId Integer userId) {

        return cardsService.getUserCards(userId);

    }

    @RequestMapping(value = "/delete-card", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ActionResult deleteCard(@RequestParam  Integer cardId) {

        return cardsService.deleteCard(cardId);

    }

    @RequestMapping(value = "/add-amount-to-balance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public  ActionResult addAmountToBalance(@ActiveUserId Integer userId, @RequestParam Double amount, @RequestParam Integer cardId) {

        return cardsService.addAmountToBalance(userId, amount, cardId);

    }

    @RequestMapping(value = "/convert-points-to-balance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public  ActionResult convertPointsToBalance(@ActiveUserId Integer userId, @RequestParam Double amount, @RequestParam Integer cardId) {

        transactionsService.substractMoneyPointFromUser(userId, amount);
        return cardsService.addAmountToBalance(userId, amount, cardId);

    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public Double getBalance(@RequestParam Integer cardId) {

        return cardsService.getBalanceOfCardById(cardId);

    }

    @RequestMapping(value = "/update-card", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ActionResult updateCard (@RequestParam Integer cardId, @RequestParam String cardName, @RequestParam String cardNo, @RequestParam String cardHolder) {

        Card card = cardsService.getCardById(cardId);
        card.setCardName(cardName);
        card.setHolderName(cardHolder);
        card.setCardNo(cardNo);

        return cardsService.updateCard(card);

    }

    @RequestMapping(value = "/recent-transactions", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public CardTransactionsResponse getRecentTransactions(@RequestParam Integer cardId) {

        return cardsService.getRecentTransactions(cardId);

    }

}
