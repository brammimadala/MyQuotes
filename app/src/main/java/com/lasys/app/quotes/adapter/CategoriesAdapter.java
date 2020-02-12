

package com.lasys.app.quotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lasys.app.quotes.R;
import com.lasys.app.quotes.activity.Quotes;
import com.lasys.app.quotes.model.categories.CatData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyHolder>
{
   private  Context mcontext;
   private OnItemClickListener mlistener;
   private List<CatData> catList ;

   public CategoriesAdapter(Context context , List<CatData> list, OnItemClickListener listener )
   {
       mcontext = context;
       mlistener = listener ;
       catList = list ;
   }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater li = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.dashboard_item_style,parent,false);

        MyHolder my = new MyHolder(v);
        return my;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position)
    {
       final CatData catData = catList.get(position) ;
       holder.textView.setText(catData.getCategoryName());

       if (catData.getCategoryImage() != null)
       {
           Picasso.get().load(catData.getCategoryImage()).into(holder.imageView);
       }

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mcontext, Quotes.class);
                intent.putExtra("Selection","CATEGORY");
                intent.putExtra("CategoryId",""+catData.getId());
                mcontext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return catList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;
        LinearLayout layout;


        public MyHolder(View itemView)
        {
            super(itemView);
            imageView   = itemView.findViewById(R.id.dis_imageView);
            textView    = itemView.findViewById(R.id.dis_TextView);
            layout      = itemView.findViewById(R.id.dis_linerLayout);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position =  getAdapterPosition();

                    if ( mlistener != null && position != RecyclerView.NO_POSITION  )
                    {
                        mlistener.onItemSelected(position);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener
    {
        void onItemSelected(int Position);
    }
}
