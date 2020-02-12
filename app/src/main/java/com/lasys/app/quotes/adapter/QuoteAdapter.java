
package com.lasys.app.quotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lasys.app.quotes.R;
import com.lasys.app.quotes.activity.QuoteDetail;
import com.lasys.app.quotes.model.authors.AuthData;
import com.lasys.app.quotes.model.quotes.Quote;
import com.lasys.app.quotes.model.quotes.QuoteData;
import com.squareup.picasso.Picasso;

import java.util.List;


public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyHolder> {
    private Context mcontext;

    List<QuoteData> mquoteData;


    public QuoteAdapter(Context context, List<QuoteData> quoteData) {
        mcontext = context;
        mquoteData = quoteData;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.quote_card_style, parent, false);

        MyHolder my = new MyHolder(v);
        return my;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final QuoteData quoteData = mquoteData.get(position);

        holder._quote_text.setText(quoteData.getQuote());
        holder._quote_author_name.setText(quoteData.getAuthorName());

        Log.i("DataValue ==> ", "" + quoteData.getAuthorName() + "\t" + quoteData.getAuthorImage() + "==> \t" + quoteData.getQuote());

        if (quoteData.getAuthorImage() != null) {
            Picasso.get().load(quoteData.getAuthorImage()).into(holder._clientProfileImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, QuoteDetail.class);
                intent.putExtra("quote", quoteData.getQuote());
                intent.putExtra("quoteAuthorName", quoteData.getAuthorName());
                intent.putExtra("quoteAuthorImage", quoteData.getAuthorImage());
                intent.putExtra("quoteId", quoteData.getId());
                //intent.putExtra("quoteAuthorImage","");
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mquoteData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView _quote_text, _quote_author_name;
        ImageView _clientProfileImage;


        public MyHolder(View itemView) {
            super(itemView);
            _quote_text = itemView.findViewById(R.id.quote_text);
            _quote_author_name = itemView.findViewById(R.id.quote_author_name);
            _clientProfileImage = itemView.findViewById(R.id.clientProfileImage);

        }

    }
}
