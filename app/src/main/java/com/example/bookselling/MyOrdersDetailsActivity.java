package com.example.bookselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bookselling.adapter.BookScreenAdapter;
import com.example.bookselling.adapter.MyOrderDetailsAdapter;
import com.example.bookselling.model.myorder.MyOrderBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrdersDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;
    @BindView(R.id.rv_my_orders_details)
    RecyclerView rvMyOrdersDetails;

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_details);
        ButterKnife.bind(this);

        //basic intialisation...
        initViews();

        if(getIntent().getParcelableExtra("my_order")!=null){
            MyOrderBO myOrderBO = getIntent().getParcelableExtra("my_order");
            if(myOrderBO.getItems().size()>0){
                tvOrderId.setText("#"+myOrderBO.getId());

                SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format1 = new SimpleDateFormat("EEE dd MMM yy");
                Date date = null;
                try {
                    date = spf.parse(myOrderBO.getOrderDateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String strDate=format1.format(date);
                tvOrderDate.setText(strDate);

                tvOrderTotal.setText(""+myOrderBO.getTotalPrice());

                MyOrderDetailsAdapter myOrderDetailsAdapter=new MyOrderDetailsAdapter(MyOrdersDetailsActivity.this, myOrderBO.getItems());
                rvMyOrdersDetails.setAdapter(myOrderDetailsAdapter);
            }
        }
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("My Orders Details");
        toolbar.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        LinearLayoutManager linearLayout=new LinearLayoutManager(MyOrdersDetailsActivity.this);
        rvMyOrdersDetails.setLayoutManager(linearLayout);
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