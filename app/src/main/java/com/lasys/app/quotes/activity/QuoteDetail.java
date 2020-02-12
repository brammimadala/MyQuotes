package com.lasys.app.quotes.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lasys.app.quotes.R;
import com.lasys.app.quotes.constants.ProgresDialog;
import com.lasys.app.quotes.constants.SharePreference;
import com.lasys.app.quotes.constants.SnackBar;
import com.lasys.app.quotes.intrface.FavCommunicator;
import com.lasys.app.quotes.model.myfav.FavDetails;
import com.lasys.app.quotes.model.myfav.FavResponse;
import com.lasys.app.quotes.network.ApiClient;
import com.lasys.app.quotes.network.ApiInterface;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteDetail extends AppCompatActivity implements View.OnClickListener,FavCommunicator {
    private TextView _qd_author_name,_qd_quote;
    private ImageView _qd_author_image ;
    private Toolbar  _toolbar_quoteDetail;
    private ImageView _back ;
    private String quote = "";
    private String quoteId = "";
    private String authorName = "";
    private String authorImage = "";
    private String status = "1";
    private View _view;
    private Menu _menu;
    private int fav = 2;
    private boolean selectionState  ;
    private SnackBar  snackBar ;
    SharePreference sharePreference;
    ProgresDialog progresDialog ;

    private int favData  ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);

        _toolbar_quoteDetail = findViewById(R.id.toolbar_quoteDetail);
        setSupportActionBar(_toolbar_quoteDetail);
        getSupportActionBar().setTitle(null);

        _view = findViewById(R.id.view) ;

        _qd_author_name = findViewById(R.id.qd_author_name);
        _qd_quote = findViewById(R.id.qd_quote);
        _qd_author_image = findViewById(R.id.qd_author_image);

        _back = findViewById(R.id.arrow_back_quoteDetail);

        progresDialog = new ProgresDialog(this);
        sharePreference = new SharePreference(QuoteDetail.this);


        snackBar = new SnackBar(this,_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            quote = extras.getString("quote");
            quoteId = extras.getString("quoteId");
            authorName = extras.getString("quoteAuthorName");
            authorImage = extras.getString("quoteAuthorImage");
            //String quoteAuthorName = extras.getString("quoteAuthorName");

            _qd_quote.setText(quote);
            _qd_author_name.setText(authorName);

            if (authorImage != null)
            {
                Picasso.get().load(authorImage).into(_qd_author_image);
            }
        }
        _back.setOnClickListener(this);

        /*if (favData.equalsIgnoreCase("selected"))
        {
            _menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.star_selected));
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        _menu = menu;

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.selection, menu);

        if (fav == 1)
        {
            _menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.star_selected)).setVisible(false);
            selectionState = false ;
        }
        if (fav == 2)
        {
            _menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.star)).setVisible(false);
            selectionState = true ;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.content_copy:
            {
                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

                // Creates a new text clip to put on the clipboard
                if (!quote.isEmpty())
                {
                    ClipData clip = ClipData.newPlainText("simple text", quote);
                    clipboard.setPrimaryClip(clip);
                    snackBar.showSnackbar("The quote has been copied to clipboard");
                }
                break;
            }

            case R.id.select:
            {
                if (selectionState)
                {
                    _menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.star_selected)).setVisible(false);
                    selectionState = false ;
                    addToMyFavCalling(1);
                }
                else
                {
                    _menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.star)).setVisible(false);
                    selectionState = true;
                    addToMyFavCalling(0);
                    //snackBar.showSnackbar("Deleted");
                }

                break;
            }
            case R.id.share:
            {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                if (!quote.isEmpty())
                {
                    sendIntent.putExtra(Intent.EXTRA_TEXT, quote);
                }
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Quotes"));
                break;
            }

        }
        return true;
    }

   private void addToMyFavCalling(final int selStatus)
   {
       progresDialog.progresDialogShow("Please wait...");

       Log.i("favDetailUserId",""+sharePreference.getUserId());
       FavDetails favDetails = new FavDetails(""+sharePreference.getUserId(),quoteId,selStatus);

       ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
       Call<FavResponse> call = apiService.createFavourite(favDetails);

       call.enqueue(new Callback<FavResponse>()
       {
           @Override
           public void onResponse(Call<FavResponse> call, Response<FavResponse> response)
           {
               FavResponse favResponse = response.body();

               Log.i("favResponse==>",""+response.code()+"\t"+response.body()+"\n"+favResponse.getStatus()+"\t"+favResponse.getMessage());

               if (favResponse.getStatus() == 201 && selStatus == 1)
               {
                 snackBar.showSnackbar("Added to My Favourites");
               }
               if (favResponse.getStatus() == 201 && selStatus == 0)
               {
                   snackBar.showSnackbar("Deleted");
               }

           }

           @Override
           public void onFailure(Call<FavResponse> call, Throwable t)
           {
               progresDialog.progresDialogDissmiss();
               Toast.makeText(QuoteDetail.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
       progresDialog.progresDialogDissmiss();
   }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.arrow_back_quoteDetail :
            {
                finish();
                break;
            }
        }
    }

    @Override
    public void respond(int data)
    {
        favData = data ;
    }

}
