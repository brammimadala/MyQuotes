
package com.lasys.app.quotes.model.quotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuoteData {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("author_id")
    @Expose
    private String authorId;

    @SerializedName("category_id")
    @Expose
    private String categoryId;

    @SerializedName("quote")
    @Expose
    private String quote;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("author_image")
    @Expose
    private String authorImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

}
