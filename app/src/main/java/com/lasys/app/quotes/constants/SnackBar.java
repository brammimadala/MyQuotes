package com.lasys.app.quotes.constants;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBar
{
    private  Context mcontext ;
    private  View mview ;
    private int duration = Snackbar.LENGTH_LONG;

    public SnackBar(Context context,View view)
    {
        mcontext = context ;
        mview = view;
    }

    public void showSnackbar(String message)
    {
        Snackbar.make(mview, message, duration).show();
    }
}
