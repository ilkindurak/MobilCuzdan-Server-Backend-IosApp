package com.mobilcuzdan.spring.mvc.models;

/**
 * Created by Fikri Can Cankurtaran on 18/12/2016.
 */

public class User {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String tcNo;
    private String phone;
    private String image;
    private String fbId;
    private String fbAccessToken;
    private String password;

    public User(){}
    public User(String name, String surname, String email, String tcNo, String phone, String image, String password, String fbId, String fbAccessToken){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.tcNo = tcNo;
        this.phone = phone;
        this.image = image;
        this.password = password;
    }
    public User(Integer userId, String name, String surname, String email, String tcNo, String phone, String image, String fbId, String fbAccessToken){
        this.id = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.tcNo = tcNo;
        this.phone = phone;
        this.image = image;
        this.fbId = fbId;
        this.fbAccessToken = fbAccessToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getTcNo() { return tcNo; }

    public void setTcNo(String tcNo) { this.tcNo = tcNo; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getFbId() { return fbId; }

    public void setFbId(String fbId) { this.fbId = fbId; }

    public String getFbAccessToken() { return fbAccessToken; }

    public void setFbAccessToken(String fbAccessToken) { this.fbAccessToken = fbAccessToken; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

}

