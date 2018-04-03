package com.gdwz.energy.web.controller.manager.dataExchange.json;

import java.io.Serializable;

/**
 * @包名称：com.gdwz.energy.web.controller.manager.dataExchange.json
 * @作者：YQ
 * @创建日期：2014/9/22
 */
public class JsonObj implements Serializable{
    private String message;

    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
