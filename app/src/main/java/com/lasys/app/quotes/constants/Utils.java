package com.lasys.app.quotes.constants;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import static android.content.Context.WIFI_SERVICE;

public class Utils
{
    public static int TYPE_NOT_CONNECTED = 0;

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;

    public static String IPaddress;
    public static  Boolean IPValue;
    public static Context mcontext;

    public Utils(Context context)
    {
        mcontext = context;
    }

    public static String getDeviceName()
    {
         return  android.os.Build.MANUFACTURER;
    }

    public static String getDeviceModel()
    {
        return   android.os.Build.MODEL;
    }

    public static String getSDK()
    {
        return String.valueOf(" SDK "+ Build.VERSION.SDK_INT+", RELEASE "+Build.VERSION.RELEASE);
    }

    public static String getRelease()
    {
        return Build.VERSION.RELEASE;
    }

    public  void hideKeyboard(@NonNull Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public  String getConnectivityStatusString(Context context) {
        int conn = Utils.getConnectivityStatus(context);
        String status = null;
        if (conn == Utils.TYPE_WIFI) {
//            status = "Wifi enabled";
        } else if (conn == Utils.TYPE_MOBILE) {
//            status = "Mobile data enabled";
        } else if (conn == Utils.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    //Check the internet connection.
    public  String networkIPAddress() {

        boolean WIFI = false;

        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo)
        {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

                if (netInfo.isConnected())

                    WIFI = true;

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

                if (netInfo.isConnected())

                    MOBILE = true;
        }

        if(WIFI == true)
        {
            IPaddress = GetDeviceipWiFiData();
            //Log.i("WIFI IP == ",IPaddress);
        }

        if(MOBILE == true)
        {
            IPaddress = GetDeviceipMobileData();
            //Log.i("MOBILE IP == ",IPaddress);
        }
        return IPaddress;
    }

    public static String GetDeviceipMobileData(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        // return inetAddress.getHostAddress().toString();
                        return ip ;
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    public static String GetDeviceipWiFiData()
    {
        WifiManager wm = (WifiManager)mcontext.getSystemService(WIFI_SERVICE);
        @SuppressWarnings("deprecation")
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

}
