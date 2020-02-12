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
import com.lasys.app.quotes.adapter.AuthorsAdapter;
import com.lasys.app.quotes.adapter.QuoteAdapter;
import com.lasys.app.quotes.constants.ProgresDialog;
import com.lasys.app.quotes.model.quotes.Quote;
import com.lasys.app.quotes.model.quotes.QuoteData;
import com.lasys.app.quotes.network.ApiClient;
import com.lasys.app.quotes.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quotes extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView ;

    private List<QuoteData> quotesList ;
    ProgresDialog progresDialog ;
    private ImageView _back ;

    private int  idType = 0 ;
    private String  authorId = null ;
    private String  authorName = null ;
    private String  authorIamge = null ;
    private String  CategoryId =  null ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        quotesList = new ArrayList<QuoteData>();

        recyclerView =  findViewById(R.id.quotesRcv);
        _back =  findViewById(R.id.arrow_back_quotes);

        progresDialog = new ProgresDialog(this);
        _back.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            String selection  = extras.getString("Selection");

            if (selection.equalsIgnoreCase("AUTHOR"))
            {
                String _authorId  = extras.getString("AuthorId");
                String _authorName  = extras.getString("AuthorName");
                String _authorImage  = extras.getString("AuthorImage");
                //Toast.makeText(this, "AUTHOR ID == "+authorId, Toast.LENGTH_SHORT).show();
                this.authorId = _authorId ;
                this.authorName = _authorName ;
                this.authorIamge = _authorImage ;
                idType = 1 ;
                quotesDataCalling(idType);
            }
            else if (selection.equalsIgnoreCase("CATEGORY"))
            {
                String categoryId  = extras.getString("CategoryId");
                //Toast.makeText(this, "CATEGORY", Toast.LENGTH_SHORT).show();
                String _authorName  = extras.getString("AuthorName");
                String _authorImage  = extras.getString("AuthorImage");
                this.authorName = _authorName ;
                this.authorIamge = _authorImage ;

                this.CategoryId = categoryId ;
                idType = 2 ;
                quotesDataCalling(idType);
            }
            else if (selection.equalsIgnoreCase("QuoteOfDay"))
            {
                //Toast.makeText(this, "TELUGU", Toast.LENGTH_SHORT).show();
                idType = 3;
                quotesDataCalling(idType);
            }
            else if (selection.equalsIgnoreCase("TELUGU"))
            {
                //Toast.makeText(this, "TELUGU", Toast.LENGTH_SHORT).show();
                  idType = 4;
                  quotesDataCalling(idType);
            }

        }
        else {
            quotesDataCalling(idType);
        }

    }

    private void quotesDataCalling(int callingTypeId )
    {
        progresDialog.progresDialogShow("Please wait...");

        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
        Call<Quote> call = null;

        //Toast.makeText(this, "callingTypeId == "+callingTypeId +"\n authorId == "+authorId+"\n CategoryId =="+CategoryId, Toast.LENGTH_SHORT).show();
        switch (callingTypeId)
        {
            case 0 :
            {
                call = apiService.getQuotes();
                break;
            }
            case 1 :
            {
                call = apiService.getAuthQuotes(authorId);
                break;
            }
            case 2 :
            {
                call = apiService.getCategoryQuotes(CategoryId);
                break;
            }
            case 3 :
            {
                call = apiService.getDayQuote();
                break;
            }
            case 4 :
            {
                //Toast.makeText(this, "TELUGU", Toast.LENGTH_SHORT).show();
                call = apiService.getTeluguQuotes();
                break;
            }

        }

        call.enqueue(new Callback<Quote>()
        {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response)
            {
                Quote quote = response.body();

                if (quote.getStatus() == 200)
                {
                    for (QuoteData quoteData : quote.getData())
                    {
                        quotesList.add(quoteData);
                    }
                    rcvDataSetting(quotesList);

                }
                else
                {
                    Toast.makeText(Quotes.this, ""+quote.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t)
            {
                progresDialog.progresDialogDissmiss();
                Log.i("onFailure == > ",t.getMessage());
                Toast.makeText(Quotes.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rcvDataSetting(List<QuoteData> authDataList)
    {

        RecyclerView.LayoutManager lm = new LinearLayoutManager(Quotes.this);
        recyclerView.setLayoutManager(lm);

        QuoteAdapter quoteAdapter  = new QuoteAdapter(Quotes.this,authDataList);
        recyclerView.setAdapter(quoteAdapter);

        progresDialog.progresDialogDissmiss();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.arrow_back_quotes :
            {
                finish();
                break;
            }
        }
    }
}
