package com.lasys.app.quotes.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.lasys.app.quotes.R;
import com.lasys.app.quotes.adapter.DashBoardRcvAdapter;
import com.lasys.app.quotes.constants.Config;
import com.lasys.app.quotes.constants.InternetPermission;
import com.lasys.app.quotes.constants.NotificationUtils;
import com.lasys.app.quotes.constants.ProgresDialog;
import com.lasys.app.quotes.constants.SharePreference;
import com.lasys.app.quotes.constants.SnackBar;
import com.lasys.app.quotes.constants.Utils;
import com.lasys.app.quotes.model.dashboard.DashboardData;
import com.lasys.app.quotes.model.dashboard.DashBordData;
import com.lasys.app.quotes.model.usercreate.UserCreate;
import com.lasys.app.quotes.model.usercreate.UserCreateData;
import com.lasys.app.quotes.model.usercreate.UserDetails;
import com.lasys.app.quotes.network.ApiClient;
import com.lasys.app.quotes.network.ApiInterface;
import com.lasys.app.quotes.smsintegra.SendSMS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity implements DashBoardRcvAdapter.DashBoardListAdapterListener {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<DashBordData> dashboardList;
    private ProgresDialog progresDialog;
    private SharePreference sharePreference;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SnackBar snackBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        recyclerView = findViewById(R.id.dashBoardRecyclerView);

        dashboardList = new ArrayList<>();
        sharePreference = new SharePreference(DashBoard.this);
        progresDialog = new ProgresDialog(DashBoard.this);

        snackBar = new SnackBar(this, recyclerView);

        if (InternetPermission.isOnline(DashBoard.this)) {
            dashboardDataCalling();
            UserRegistration();
        } else {
            snackBar.showSnackbar("Please Check internet connection");
            //Toast.makeText(this, "Please Check internet connection", Toast.LENGTH_SHORT).show();
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    //displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);

                    NotificationUtils notificationUtils = new NotificationUtils(DashBoard.this);

                }
            }
        };

        // Log.i("FBToken ===> ",sharePreference.getFBToken()) ;
    }

    private void dashboardDataCalling() {
        progresDialog.progresDialogShow("Please wait....");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DashboardData> call = apiService.getDashBoardResponse();

        call.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                DashboardData dashboardData = response.body();

                if (dashboardData.getStatus() == 200) {
                    for (DashBordData dashBordData : dashboardData.getData()) {
                        dashboardList.add(dashBordData);
                    }

                    recyclerViewDataSetting(dashboardList);

                } else {
                    Toast.makeText(DashBoard.this, "" + dashboardData.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                progresDialog.progresDialogDissmiss();

                Log.i("onFailure == > ", t.getMessage());
                Toast.makeText(DashBoard.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //** RecyclerView Data Setting START **//
    private void recyclerViewDataSetting(List<DashBordData> list) {
        //Horizontal View
        //RecyclerView.LayoutManager lm = new LinearLayoutManager(DashBoardActivity.this);

        //Vertical View
        gridLayoutManager = new GridLayoutManager(DashBoard.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //DashBoardRcvAdapter
        DashBoardRcvAdapter dashBoardRcvAdapter = new DashBoardRcvAdapter(DashBoard.this, list, this);

        //Assigning DadhboardAddapter to RecyclerView
        recyclerView.setAdapter(dashBoardRcvAdapter);

        progresDialog.progresDialogDissmiss();

    }
    //** RecyclerView Data Setting END **//

    //RecyclerView onItemSelected Start//
    @Override
    public void onItemSelected(int Position) {

        DashBordData dashBordData = dashboardList.get(Position);
        int id = dashBordData.getId();
        switch (id) {
            case 1: {
                Intent intent = new Intent(this, Quotes.class);
                startActivity(intent);
                break;
            }
            case 2: {
                Intent intent = new Intent(this, Authors.class);
                startActivity(intent);
                break;
            }
            case 3: {
                Intent intent = new Intent(this, Categories.class);
                startActivity(intent);
                break;
            }
            case 4: {
                Intent intent = new Intent(this, Quotes.class);
                intent.putExtra("Selection", "QuoteOfDay");
                startActivity(intent);
                break;
            }
            case 5: {
                //Telugu Quotes

                Intent intent = new Intent(this, Quotes.class);
                intent.putExtra("Selection", "TELUGU");
                startActivity(intent);
                break;
            }
            case 6: {
                Intent intent = new Intent(this, Quotes.class);
                startActivity(intent);
                break;
            }
            case 7: {
                // Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.lasys.app.quotes"));
                startActivity(intent);

               /* Toast.makeText(this, "SMS Trying", Toast.LENGTH_SHORT).show();

               Thread smsThred = new Thread()
               {
                   @Override
                   public void run()
                   {
                       super.run();
                       SendSMS sendSMSget = new SendSMS();
                       String response = sendSMSget.sendSms();
                       Log.i("SMSresponse ==>",""+response);
                   }
               };
                smsThred.start();*/

                break;
            }
        }

    }
    //RecyclerView onItemSelected End//

    private void UserRegistration() {

        if (sharePreference.getRGFlag()) {
            //Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show();
            Log.i("FLAG 1 ==>", "Registration Successfull \t" + sharePreference.getRGFlag());
            sendRegistrationToServer(sharePreference.getFBToken());
        } else {
            Log.i("FLAG 2 ==>", "Device Already Registered \t" + sharePreference.getRGFlag());
            //Toast.makeText(this, "Device Already Registered", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendRegistrationToServer(String token) {
        progresDialog.progresDialogShow("Please wait...");
        UserDetails userDetails = new UserDetails(token, Utils.getDeviceName(), Utils.getDeviceModel(), Utils.getSDK());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserCreate> call = apiService.userCreate(userDetails);

        //sharePreference.setTokenFlag(false);

        call.enqueue(new Callback<UserCreate>() {
            @Override
            public void onResponse(Call<UserCreate> call, Response<UserCreate> response) {
                UserCreate userCreate = response.body();
                UserCreateData userCreateData = userCreate.getUserCreateData();

                if (userCreate.getStatus() == 201) {
                    sharePreference.setUserId(userCreateData.getUserId());
                    Log.i("USER_ID ==>", "" + userCreateData.getUserId());
                    //Toast.makeText(DashBoard.this, "MyUserId == "+sharePreference.getUserId(), Toast.LENGTH_SHORT).show();
                    sharePreference.setRGFlag(false);
                    Log.i("RgFlag ==>", "" + sharePreference.getRGFlag());
                } else {
                    Toast.makeText(DashBoard.this, "" + userCreate.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserCreate> call, Throwable t) {
                progresDialog.progresDialogDissmiss();
                Log.d("onFailure == > ", "" + t.getMessage());
                Toast.makeText(DashBoard.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        progresDialog.progresDialogDissmiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
