package com.example.bookselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bookselling.adapter.AddToCartAdapter;
import com.example.bookselling.model.product.ProductBO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class AddToCartActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rv_add_to_cart)
    RecyclerView rvAddToCart;
    @BindView(R.id.tv_total_amount)
    public TextView tvTotalAmount;
    @BindView(R.id.btn_checkout)
    AppCompatButton btnCheckout;
    private ArrayList<ProductBO> productBOArrayList=new ArrayList<>();
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNoData;
    @BindView(R.id.rl_mainL_layout)
    RelativeLayout rlMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        ButterKnife.bind(this);

        //basic intialisation...
        initViews();

        if(Paper.book().read("product_cart") !=null){
            productBOArrayList=Paper.book().read("product_cart");
            if(productBOArrayList.size()>0){
                rlMainLayout.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                AddToCartAdapter addToCartAdapter=new AddToCartAdapter(AddToCartActivity.this,productBOArrayList,AddToCartActivity.this);
                rvAddToCart.setAdapter(addToCartAdapter);

                int sum = 0;
                for(int i = 0; i < productBOArrayList.size(); i++){
                    sum += productBOArrayList.get(i).getProductTotal();
                }
                tvTotalAmount.setText(""+sum);
            }else{
                rlMainLayout.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }

        }else{
            rlMainLayout.setVisibility(View.GONE);
            rlNoData.setVisibility(View.VISIBLE);
        }
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.toolbar_add_to_cart);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("My Cart");
        toolbar.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        rvAddToCart.setLayoutManager(new LinearLayoutManager(AddToCartActivity.this));

        btnCheckout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_checkout) {

        }else if(v.getId()== R.id.btn_shopping_now){
            startActivity(new Intent(AddToCartActivity.this,DashBoardActivity.class));
            finish();
        }
    }

    public void getTotal(int total){
        tvTotalAmount.setText(""+total);
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