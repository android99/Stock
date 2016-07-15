package com.tom.stock.backend;

/**
 * Created by Administrator on 2016/7/15.
 */
public class LoginValidation {
    String userid;
    int resultCode;
    String message;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
