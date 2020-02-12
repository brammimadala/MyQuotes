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
import com.lasys.app.quotes.constants.ProgresDialog;
import com.lasys.app.quotes.model.authors.AuthData;
import com.lasys.app.quotes.model.authors.AuthorsData;
import com.lasys.app.quotes.network.ApiClient;
import com.lasys.app.quotes.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authors extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<AuthData> authDataList;
    ProgresDialog progresDialog;
    private ImageView _back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        authDataList = new ArrayList<>();

        recyclerView = findViewById(R.id.authorsRcv);
        _back = findViewById(R.id.arrow_back_authors);
        progresDialog = new ProgresDialog(this);

        _back.setOnClickListener(this);

        authorsDataCalling();
    }

    private void authorsDataCalling() {
        progresDialog.progresDialogShow("Please wait...");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AuthorsData> call = apiService.getAuthors();

        call.enqueue(new Callback<AuthorsData>() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onResponse(Call<AuthorsData> call, Response<AuthorsData> response) {
                AuthorsData authorsData = response.body();

                if (authorsData.getStatus() == 200) {
                    for (AuthData authData : authorsData.getData()) {
                        authDataList.add(authData);
                    }
                    rcvDataSetting(authDataList);
                } else {
                    Toast.makeText(Authors.this, "" + authorsData.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthorsData> call, Throwable t) {
                progresDialog.progresDialogDissmiss();
                Log.i("onFailure == > ", t.getMessage());
                Toast.makeText(Authors.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void rcvDataSetting(List<AuthData> authDataList) {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(Authors.this);
        recyclerView.setLayoutManager(lm);

        AuthorsAdapter authorsAdapter = new AuthorsAdapter(Authors.this, authDataList);
        recyclerView.setAdapter(authorsAdapter);

        progresDialog.progresDialogDissmiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_back_authors: {
                finish();
                break;
            }
        }
    }
}
