package com.example.bookselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bookselling.adapter.MyOrdersAdapter;
import com.example.bookselling.adapter.VideoGalleryAdapter;
import com.example.bookselling.model.myorder.MyOrderResponse;
import com.example.bookselling.model.videogallery.VideoGalleryBO;
import com.example.bookselling.model.videogallery.VideoGalleryResponse;
import com.example.bookselling.services.ApiRequestHelper;
import com.example.bookselling.services.BookSelling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrdersActivity extends AppCompatActivity {

    @BindView(R.id.rv_my_orders)
    RecyclerView rvMyOrders;
    private BookSelling bookSelling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ButterKnife.bind(this);
        bookSelling=(BookSelling)getApplication();

        //basic intialisation...
        initViews();

        //load my orders data and append it to recycler view..
        loadData();
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("My Orders");
        toolbar.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        LinearLayoutManager linearLayout=new LinearLayoutManager(MyOrdersActivity.this);
        rvMyOrders.setLayoutManager(linearLayout);
    }

    private void loadData(){
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Please wait....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        Map<String, String> params=new HashMap<>();
        params.put("userid","1");

        bookSelling.getApiRequestHelper().myOrder(params,new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                MyOrderResponse myOrderResponse = (MyOrderResponse) object;
                //Log.e(TAG, "onSuccess: " + loginResponse.getResponsecode());
                //  Log.e(TAG, "onSuccess: " + loginResponse.getMessage());
                progress.dismiss();
                if (myOrderResponse.getResponsecode() == 200) {
                    Toast.makeText(MyOrdersActivity.this,myOrderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    //Attach data to custom recycler view..
                    if(myOrderResponse.getData().size()>0){
                        MyOrdersAdapter myOrderAdapter=new MyOrdersAdapter(MyOrdersActivity.this,myOrderResponse.getData());
                        rvMyOrders.setAdapter(myOrderAdapter);
                    }
                } else {
                    Toast.makeText(MyOrdersActivity.this,myOrderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(MyOrdersActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}