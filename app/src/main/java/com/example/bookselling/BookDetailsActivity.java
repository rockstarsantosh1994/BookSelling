package com.example.bookselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookselling.model.product.ProductBO;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_product_img)
    ImageView ivProductImage;
    @BindView(R.id.tv_product_name)
    TextView tvProductTitle;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.tv_product_description)
    TextView tvProductDescription;
    @BindView(R.id.btn_add_to_cart)
    AppCompatButton btnAddToCart;
    @BindView(R.id.btn_buy_now)
    AppCompatButton btnBuyNow;
    private ArrayList<ProductBO> productBOArrayList=new ArrayList<>();
    private TextView tvCount;
    private ImageView ivCart;
    private ProductBO productBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ButterKnife.bind(this);
        Paper.init(BookDetailsActivity.this);
        //basic intialisation..
        initViews();

        //Checking if their any product available in Cart or not
        if(Paper.book().read("product_cart")!=null){
            productBOArrayList=Paper.book().read("product_cart");
            if(productBOArrayList.size()==0){
                tvCount.setVisibility(View.GONE);
            }else{
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(""+productBOArrayList.size());
            }

        }

        if(getIntent().getParcelableExtra("productbo")!=null){
            productBO=getIntent().getParcelableExtra("productbo");

            Glide.with(BookDetailsActivity.this).load(productBO.getCoverPhoto()).error(R.drawable.ic_launcher_background).into(ivProductImage);

            tvProductTitle.setText(productBO.getTitle());
            tvProductPrice.setText("₹" +productBO.getPrice() + " /-");
            tvProductDescription.setText(productBO.getDetails());
        }
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Book Detail");
        toolbar.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        tvCount=toolbar.findViewById(R.id.tv_count);
        ivCart=toolbar.findViewById(R.id.iv_shopping_cart);

        //Click listeners..
        btnAddToCart.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        ivProductImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_to_cart:
                if(productBOArrayList.contains(productBO)){
                    Paper.book().write("product_cart",productBOArrayList);
                    Toast.makeText(BookDetailsActivity.this, "Product already added!", Toast.LENGTH_SHORT).show();
                }else{
                    productBOArrayList.add(new ProductBO(productBO.getId(),
                            productBO.getTitle(),
                            productBO.getDetails(),
                            productBO.getPrice(),
                            productBO.getCoverPhoto(),
                            1,
                            productBO.getIsActive(),
                            productBO.getPrice()*productBO.getQuantity()));
                    Paper.book().write("product_cart",productBOArrayList);
                    Toast.makeText(BookDetailsActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
                }
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(""+productBOArrayList.size());
                break;

            case R.id.btn_buy_now:
                /*if(productBOArrayList.contains(productBO)){
                    Paper.book().write("product_cart",productBOArrayList);
                    Intent intent=new Intent(BookInfoActivity.this,AddToCartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    productBOArrayList.add(new ProductBO(productBO.getId(),
                            productBO.getTitle(),
                            productBO.getDetails(),
                            productBO.getPrice(),
                            productBO.getCoverPhoto(),
                            1,
                            productBO.getIsActive(),
                            productBO.getPrice()*productBO.getQuantity()));

                    Paper.book().write("product_cart",productBOArrayList);
                    Intent intent=new Intent(BookInfoActivity.this,AddToCartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }*/
                break;

            case R.id.iv_shopping_cart:
                Intent intent=new Intent(BookDetailsActivity.this,AddToCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.iv_product_img:
                intent=new Intent(BookDetailsActivity.this,PreviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("image_url",productBO.getCoverPhoto());
                startActivity(intent);
                break;
        }
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