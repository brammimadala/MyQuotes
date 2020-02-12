package com.lasys.app.quotes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lasys.app.quotes.R;
import com.lasys.app.quotes.adapter.CategoriesAdapter;
import com.lasys.app.quotes.constants.ProgresDialog;
import com.lasys.app.quotes.model.categories.CategoriesData;
import com.lasys.app.quotes.model.categories.CatData;
import com.lasys.app.quotes.network.ApiClient;
import com.lasys.app.quotes.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categories extends AppCompatActivity implements CategoriesAdapter.OnItemClickListener, View.OnClickListener {

    private RecyclerView recyclerView ;
    private GridLayoutManager gridLayoutManager ;

    private List<CatData> catList ;
    ProgresDialog progresDialog ;

    private ImageView _back ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recyclerView =  findViewById(R.id.categoriesRecyclerView);
        _back        =  findViewById(R.id.arrow_back_categories);

        progresDialog = new ProgresDialog(Categories.this);
        catList = new ArrayList<>();

        _back.setOnClickListener(this);
        categoriesDataCalling();

    }

    private void categoriesDataCalling()
    {
        progresDialog.progresDialogShow("Please wait...");

        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
        Call<CategoriesData> call = apiService.getCategories();

        call.enqueue(new Callback<CategoriesData>()
        {
            @Override
            public void onResponse(Call<CategoriesData> call, Response<CategoriesData> response)
            {
                CategoriesData categoriesData = response.body() ;

                if (categoriesData.getStatus() == 200)
                {
                    for (CatData catData : categoriesData.getData())
                    {
                        catList.add(catData);
                    }
                    recyclerViewDataSetting(catList);

                }
                else
                {
                    Toast.makeText(Categories.this, ""+categoriesData.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoriesData> call, Throwable t)
            {
                progresDialog.progresDialogDissmiss();
                Log.i("onFailure == > ",t.getMessage());
                Toast.makeText(Categories.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    //** RecyclerView Data Setting START **//
    private void recyclerViewDataSetting(List<CatData> data)
    {
        //Horizontal View
        //RecyclerView.LayoutManager lm = new LinearLayoutManager(DashBoardActivity.this);

        //Vertical View
        gridLayoutManager = new GridLayoutManager(Categories.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //DashBoardRcvAdapter

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this,data,this);
        //Assigning Addapter to RecyclerView
        recyclerView.setAdapter(categoriesAdapter);

        progresDialog.progresDialogDissmiss();

    }
     //** RecyclerView Data Setting END **//

    @Override
    public void onItemSelected(int Position)
    {
        Toast.makeText(this, "Position == >"+Position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.arrow_back_categories :
            {
                finish();
                break;
            }
        }
    }
}
