package com.app.common;

/**
 * Created by localuser on 2017/8/29.
 */
public class RetResult<T> {
    private String retMessage;
    private T retObj = null;

    public RetResult() {
    }

    public RetResult(String retMessage, T retObj) {
        this.retMessage = retMessage;
        this.retObj = retObj;
    }

    public RetResult(String retMessage) {
        this.retMessage = retMessage;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public T getRetObj() {
        return retObj;
    }

    public void setRetObj(T retObj) {
        this.retObj = retObj;
    }
}
