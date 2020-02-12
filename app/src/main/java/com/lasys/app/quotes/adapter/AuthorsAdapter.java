
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
import com.lasys.app.quotes.activity.Quotes;
import com.lasys.app.quotes.model.authors.AuthData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.MyHolder> {
    private Context mcontext;

    List<AuthData> mauthDataList;

    public AuthorsAdapter(Context context, List<AuthData> authDataList) {
        mcontext = context;
        mauthDataList = authDataList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.author_card_style, parent, false);

        MyHolder my = new MyHolder(v);
        return my;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final AuthData authData = mauthDataList.get(position);

        holder._authorName.setText(authData.getAuthorName());

        if (authData.getAuthorImage() != null) {
            Picasso.get().load(authData.getAuthorImage()).into(holder._authorImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mcontext, ""+authData.getAuthorName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mcontext, Quotes.class);
                intent.putExtra("Selection", "AUTHOR");
                intent.putExtra("AuthorId", "" + authData.getId());
                intent.putExtra("AuthorName", "" + authData.getAuthorName());
                intent.putExtra("AuthorImage", "" + authData.getAuthorImage());

                Log.i("AuthorId====>", "" + authData.getId() + "\t" + authData.getAuthorName() + "\t" + authData.getAuthorImage());
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mauthDataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView _authorName;
        ImageView _authorImage;

        public MyHolder(View itemView) {
            super(itemView);
            _authorName = itemView.findViewById(R.id.authorName);
            _authorImage = itemView.findViewById(R.id.authorProfileImage);

        }

    }
}
