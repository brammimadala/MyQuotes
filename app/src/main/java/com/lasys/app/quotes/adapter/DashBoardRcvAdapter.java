

package com.lasys.app.quotes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lasys.app.quotes.R;
import com.lasys.app.quotes.model.dashboard.DashBordData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashBoardRcvAdapter extends RecyclerView.Adapter<DashBoardRcvAdapter.MyHolder>
{
   private  Context mcontext;
   private List<DashBordData> dashBordDataList;
   private DashBoardListAdapterListener mlistener;

   public DashBoardRcvAdapter(Context context , List<DashBordData> list, DashBoardListAdapterListener listener)
   {
       mcontext = context;
       mlistener = listener ;
       dashBordDataList = list ;
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
        DashBordData dashBordData = dashBordDataList.get(position) ;
        holder.textView.setText(dashBordData.getMenuName());

        if (dashBordData.getMenuImage() != null)
        {
            Picasso.get().load(dashBordData.getMenuImage()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount()
    {
        return dashBordDataList.size();
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

    public interface DashBoardListAdapterListener
    {
        void onItemSelected(int Position);
    }
}
