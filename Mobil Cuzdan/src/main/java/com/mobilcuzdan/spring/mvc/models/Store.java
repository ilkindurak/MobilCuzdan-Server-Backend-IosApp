package com.mobilcuzdan.spring.mvc.models;

/**
 * Created by fikricanca on 31.12.2016.
 */
public class Store {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Float creditBalance;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Float getCreditBalance() { return creditBalance; }

    public void setCreditBalance(Float creditBalance) { this.creditBalance = creditBalance; }
}
