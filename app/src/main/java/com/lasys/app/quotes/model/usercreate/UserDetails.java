
package com.lasys.app.quotes.model.usercreate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("device_name")
    @Expose
    public String deviceName;
    @SerializedName("device_model")
    @Expose
    public String deviceModel;
    @SerializedName("device_version")
    @Expose
    public String deviceVersion;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserDetails() {
    }

    /**
     * 
     * @param deviceName
     * @param token
     * @param deviceModel
     * @param deviceVersion
     */
    public UserDetails(String token, String deviceName, String deviceModel, String deviceVersion) {
        super();
        this.token = token;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.deviceVersion = deviceVersion;
    }

}
