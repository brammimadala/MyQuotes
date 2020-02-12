package com.lasys.app.quotes.constants;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreference
{
    Context mContext;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor preferenceEditor;

    public SharePreference(Context context)
    {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(SpConstant.PREFERNCES_Key, Context.MODE_PRIVATE);
    }

    public void setFBToken(String value)
    {
        preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putString(SpConstant.FB_TOKEN_Key, value);
        preferenceEditor.commit();
    }

    public String getFBToken()
    {
        return sharedPreferences.getString(SpConstant.FB_TOKEN_Key,null);
    }

    public void setUserId(int value)
    {
        preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putInt(SpConstant.USER_ID_Key, value);
        preferenceEditor.commit();
    }

    public int getUserId()
    {
        return sharedPreferences.getInt(SpConstant.USER_ID_Key,0);
    }

    public void setRGFlag(boolean value)
    {
        preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putBoolean(SpConstant.RG_TokenFlag, value);
        preferenceEditor.commit();
    }

    public boolean getRGFlag()
    {
        return sharedPreferences.getBoolean(SpConstant.RG_TokenFlag,true);
    }

    public void setFavFlag(boolean value)
    {
        preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putBoolean(SpConstant.MyFav_Flag, value);
        preferenceEditor.commit();
    }

    public boolean getFavFlag()
    {
        return sharedPreferences.getBoolean(SpConstant.MyFav_Flag,true);
    }

    public void clearAll()
    {
        SharedPreferences settings = mContext.getSharedPreferences(SpConstant.PREFERNCES_Key, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

}
