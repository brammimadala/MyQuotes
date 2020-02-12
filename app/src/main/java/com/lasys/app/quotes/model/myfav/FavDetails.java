
package com.lasys.app.quotes.model.myfav;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavDetails {

    @SerializedName("user_id")
    @Expose
    public String userId;

    @SerializedName("quote_id")
    @Expose
    public String quoteId;
    @SerializedName("status")
    @Expose
    public int status;

    /**
     * No args constructor for use in serialization
     * 
     */


    /**
     * 
     * @param quoteId
     * @param status
     * @param userId
     */
    public FavDetails(String userId, String quoteId, int status) {
        super();
        this.userId = userId;
        this.quoteId = quoteId;
        this.status = status;
    }

}
