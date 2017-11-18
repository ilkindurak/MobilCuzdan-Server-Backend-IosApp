package com.mobilcuzdan.spring.mvc.models;

import java.util.List;

/**
 * Created by fikricanca on 7.01.2017.
 */
public class CardTransactionsResponse {

    private Card card;
    private List<Transaction> recentTransactions;

    public CardTransactionsResponse(){};

    public CardTransactionsResponse(Card card, List<Transaction> recentTransactions) {

        this.card = card;
        this.recentTransactions = recentTransactions;

    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Transaction> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<Transaction> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
