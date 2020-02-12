package com.lasys.app.quotes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lasys.app.quotes.R;
import com.lasys.app.quotes.adapter.QuoteAdapter;
import com.lasys.app.quotes.constants.ProgresDialog;
import com.lasys.app.quotes.constants.SharePreference;
import com.lasys.app.quotes.constants.SnackBar;
import com.lasys.app.quotes.model.quotes.Quote;
import com.lasys.app.quotes.model.quotes.QuoteData;
import com.lasys.app.quotes.network.ApiClient;
import com.lasys.app.quotes.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFavourites extends AppCompatActivity implements View.OnClickListener
{
    ProgresDialog progresDialog ;

    private SharePreference sharePreference ;

    private RecyclerView  _myFavRcv ;
    private List<QuoteData> quotesList ;
    private SnackBar snackBar ;
    private QuoteDetail quoteDetail;

    private ImageView _back ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourites);

        _myFavRcv = findViewById(R.id.myFavRcv);
        _back = findViewById(R.id.arrow_back_favourites);

        snackBar = new SnackBar(this,_back);

        quotesList = new ArrayList<QuoteData>();
        progresDialog = new ProgresDialog(this);
        sharePreference = new SharePreference(getApplicationContext());
        _back.setOnClickListener(this);
        quoteDetail = new QuoteDetail();
        myFavQuotesDataCalling();
    }

    private void myFavQuotesDataCalling()
    {
        progresDialog.progresDialogShow("Please wait...");

        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);

        Call<Quote> call = apiService.getMyFavourite(sharePreference.getUserId());

        call.enqueue(new Callback<Quote>()
        {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response)
            {
                Log.i("MyFavResponse ==>",""+response.body());
                Quote quote = response.body() ;
                if (quote.getStatus() == 200)
                {
                    for (QuoteData quoteData : quote.getData())
                    {
                        quotesList.add(quoteData);
                    }
                    rcvDataSetting(quotesList);

                    if (quotesList.size() > 0)
                    {
                        quoteDetail.respond(1);
                    }
                }
                else
                {
                    Toast.makeText(MyFavourites.this, ""+quote.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t)
            {
                progresDialog.progresDialogDissmiss();
                Log.i("onFailure == > ",t.getMessage());
                Toast.makeText(MyFavourites.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rcvDataSetting(List<QuoteData> authDataList)
    {
       if (authDataList.isEmpty())
       {
           snackBar.showSnackbar("You don't have any favourites");

       }else
       {

           Log.i("boolean 1 ==> ",""+sharePreference.getFavFlag());
           sharePreference.setFavFlag(true);
           Log.i("boolean 2 ==> ",""+sharePreference.getFavFlag());
       }

        RecyclerView.LayoutManager lm = new LinearLayoutManager(MyFavourites.this);
        _myFavRcv.setLayoutManager(lm);

        QuoteAdapter quoteAdapter  = new QuoteAdapter(MyFavourites.this,authDataList);
        _myFavRcv.setAdapter(quoteAdapter);

        progresDialog.progresDialogDissmiss();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.arrow_back_favourites :
            {
                finish();
                break;
            }
        }
    }

}
