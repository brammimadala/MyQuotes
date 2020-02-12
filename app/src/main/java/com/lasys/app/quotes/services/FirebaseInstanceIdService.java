package com.lasys.app.quotes.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.lasys.app.quotes.constants.Config;
import com.lasys.app.quotes.constants.SharePreference;

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService
{

    private static final String TAG = FirebaseInstanceIdService.class.getSimpleName();
    SharePreference sharePreference ;

    /*private static final String REG_TOKEN = "REG_TOKEN";
    SharePreference sharePreference ;

    @Override
    public void onTokenRefresh()
    {
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN, recentToken);

        sharePreference = new SharePreference(getApplicationContext());
        sharePreference.setFBToken(recentToken);

        Log.i("recentToken ==> ",recentToken);
    }*/


    @Override
    public void onTokenRefresh()
    {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token)
    {

        sharePreference = new SharePreference(getApplicationContext());
        sharePreference.setFBToken(token);

        /*SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();*/
    }




}

