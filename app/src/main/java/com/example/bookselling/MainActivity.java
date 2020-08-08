package com.example.bookselling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bookselling.adapter.BookScreenAdapter;
import com.example.bookselling.model.product.ProductResponse;
import com.example.bookselling.services.ApiRequestHelper;
import com.example.bookselling.services.BookSelling;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_book)
    RecyclerView rvBook;
    BookSelling bookSelling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bookSelling=(BookSelling) getApplication();

        //basic intialisation...
        initViews();

        //load book data....
        loadData();
    }

    private void initViews(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
        rvBook.setLayoutManager(linearLayoutManager);
    }

    private void loadData(){
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Please wait....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        bookSelling.getApiRequestHelper().getproductdata(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                ProductResponse productResponse = (ProductResponse) object;
                //Log.e(TAG, "onSuccess: " + loginResponse.getResponsecode());
                //  Log.e(TAG, "onSuccess: " + loginResponse.getMessage());
                progress.dismiss();
                if (productResponse.getResponsecode() == 200) {
                    if(productResponse.getData().size()>0){
                        BookScreenAdapter bookScreenAdapter=new BookScreenAdapter(MainActivity.this,productResponse.getData());
                        rvBook.setAdapter(bookScreenAdapter);
                    }
                    Toast.makeText(MainActivity.this,productResponse.getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this,productResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }
}