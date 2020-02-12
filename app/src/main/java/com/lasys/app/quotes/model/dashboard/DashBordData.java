
package com.lasys.app.quotes.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashBordData {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    @SerializedName("menu_image")
    @Expose
    private String menuImage;

    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

}
