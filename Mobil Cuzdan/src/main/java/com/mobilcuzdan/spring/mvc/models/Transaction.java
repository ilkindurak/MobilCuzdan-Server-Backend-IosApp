package com.mobilcuzdan.spring.mvc.models;

/**
 * Created by fikricanca on 31.12.2016.
 */
public class Transaction {

    private Integer id;
    private String date;
    private Integer userId;
    private Integer cardId;
    private Integer storeId;
    private String storeName;
    private Double amount;
    private Integer type; //1 harcama, 2 yükleme

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() { return  storeName; }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() { return type; }

    public void setType(Integer type) { this.type = type; }
}
