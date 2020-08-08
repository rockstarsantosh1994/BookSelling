package com.example.bookselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookselling.model.product.ProductBO;

import org.w3c.dom.Text;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookInfoActivity extends AppCompatActivity {

    @BindView(R.id.iv_product_img)
    ImageView ivProductImage;
    @BindView(R.id.tv_product_name)
    TextView tvProductTitle;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.tv_product_description)
    TextView tvProductDescription;
    private ProductBO productBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        ButterKnife.bind(this);

        //basic intialisation..
        initViews();

        if(getIntent().getParcelableExtra("productbo")!=null){
            productBO=getIntent().getParcelableExtra("productbo");

            Glide.with(BookInfoActivity.this).load(productBO.getCoverPhoto()).error(R.drawable.ic_launcher_background).into(ivProductImage);

            tvProductTitle.setText(productBO.getTitle());
            tvProductPrice.setText("₹" +productBO.getPrice() + " /-");
            tvProductDescription.setText(productBO.getDetails());
        }
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Book Detail");
        toolbar.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
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