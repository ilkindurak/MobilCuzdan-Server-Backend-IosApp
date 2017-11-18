package com.mobilcuzdan.spring.mvc.models;

/**
 * Created by fikricanca on 18.12.2016.
 */
public class LoginSignupResponse {

    private ActionResult actionResult;
    private User user;
    private String token;

    public LoginSignupResponse() {
    }

    public LoginSignupResponse(ActionResult actionResult) {
        this.setActionResult(actionResult);
    }

    public LoginSignupResponse(ActionResult actionResult, User user, String token) {
        this.setActionResult(actionResult);
        this.setUser(user);
        this.setToken(token);
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
