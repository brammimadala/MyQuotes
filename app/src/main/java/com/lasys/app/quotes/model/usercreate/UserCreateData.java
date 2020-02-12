
package com.lasys.app.quotes.model.usercreate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCreateData {

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
