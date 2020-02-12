package com.lasys.app.quotes.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.lasys.app.quotes.R;
import com.lasys.app.quotes.constants.ProgresDialog;
import com.lasys.app.quotes.constants.SharePreference;
import com.lasys.app.quotes.constants.Utils;

import static com.lasys.app.quotes.intrface.AppConstants.SPLASHTIME;

public class SplashActivity extends AppCompatActivity
{
    ProgresDialog progresDialog ;
    SharePreference sharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_splash);

//Logo Timer
        Thread logoTimer = new Thread()
        {
            public void run()
            {
                try {
                    int logoTimer = 0;
                    while (logoTimer < SPLASHTIME)
                    {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }


                    Intent intent = new Intent(SplashActivity.this, DashBoard.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally
                {
                    finish();
                }
            }
        };
        logoTimer.start();
    }


}
