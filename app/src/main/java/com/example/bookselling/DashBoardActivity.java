package com.example.bookselling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_book_data)
    AppCompatButton btnBookData;
    @BindView(R.id.btn_my_orders)
    AppCompatButton btnMyOrders;
    @BindView(R.id.btn_video_gallery)
    AppCompatButton btnVideoGallery;
    @BindView(R.id.btn_success_story)
    AppCompatButton btnSuccessStory;
    @BindView(R.id.btn_add_to_cart)
    AppCompatButton btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        Paper.init(DashBoardActivity.this);

        btnBookData.setOnClickListener(this);
        btnMyOrders.setOnClickListener(this);
        btnVideoGallery.setOnClickListener(this);
        btnSuccessStory.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_book_data:
                startActivity(new Intent(DashBoardActivity.this,BooksDataActivity.class));
                break;

            case R.id.btn_my_orders:
                startActivity(new Intent(DashBoardActivity.this,MyOrdersActivity.class));
                break;

            case R.id.btn_video_gallery:
                startActivity(new Intent(DashBoardActivity.this,VideoGalleryActivity.class));
                break;

            case R.id.btn_success_story:
                startActivity(new Intent(DashBoardActivity.this,SuccessStoryActivity.class).putExtra("type","success_story"));
                break;

            case R.id.btn_add_to_cart:
                startActivity(new Intent(DashBoardActivity.this,AddToCartActivity.class));
                break;

        }
    }
}