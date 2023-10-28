package com.example.myhisabadmin.Model.AdminUserList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminUserList {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Datas data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Datas getData() {
        return data;
    }

    public void setData(Datas data) {
        this.data = data;
    }

}
