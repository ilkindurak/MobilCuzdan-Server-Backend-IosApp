package com.mobilcuzdan.spring.mvc.models;

/**
 * Created by fikricanca on 24.12.2016.
 */
public class Card {

    private Integer id;
    private String holderName;
    private String cardNo;
    private String cardName;
    private String image;
    private Double balance;

    public Card(){}
    public Card(Integer cardId, String holderName, String cardNo, String expDate, String image, Double balance){
        this.id = cardId;
        this.holderName = holderName;
        this.cardNo = cardNo;
        this.cardName = expDate;
        this.image = image;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String expDate) {
        this.cardName = expDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getBalance() { return  balance; }

    public void setBalance(Double balance) { this.balance = balance; }
}
