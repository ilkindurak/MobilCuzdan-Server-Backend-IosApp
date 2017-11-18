package com.mobilcuzdan.spring.mvc.models;

/**
 * Created by YunusS on 3/28/2016.
 */
public class ActionResult {
    private Boolean success;
    private String message;
    private Object data;

    public ActionResult() {
        this(false, "Error");
    }

    public ActionResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
