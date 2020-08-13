package com.example.bookselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookselling.adapter.AddToCartAdapter;
import com.example.bookselling.adapter.MyOrdersAdapter;
import com.example.bookselling.model.CreateOrderResponse;
import com.example.bookselling.model.myorder.MyOrderResponse;
import com.example.bookselling.model.product.ProductBO;
import com.example.bookselling.services.ApiRequestHelper;
import com.example.bookselling.services.BookSelling;
import com.example.bookselling.utility.AvenuesParams;
import com.example.bookselling.utils.AllKeys;
import com.example.bookselling.utils.CommonMethods;
import com.example.bookselling.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<ProductBO> productBOArrayList = new ArrayList<>();
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNoData;
    @BindView(R.id.rl_mainL_layout)
    RelativeLayout rlMainLayout;
    private BookSelling bookSelling;
    private String TAG = "AddToCartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        ButterKnife.bind(this);
        bookSelling = (BookSelling) getApplication();

        //basic intialisation...
        initViews();

        if (Paper.book().read("product_cart") != null) {
            productBOArrayList = Paper.book().read("product_cart");
            if (productBOArrayList.size() > 0) {
                rlMainLayout.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                AddToCartAdapter addToCartAdapter = new AddToCartAdapter(AddToCartActivity.this, productBOArrayList, AddToCartActivity.this);
                rvAddToCart.setAdapter(addToCartAdapter);

                int sum = 0;
                for (int i = 0; i < productBOArrayList.size(); i++) {
                    sum += productBOArrayList.get(i).getProductTotal();
                }
                tvTotalAmount.setText("" + sum);
            } else {
                rlMainLayout.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }

        } else {
            rlMainLayout.setVisibility(View.GONE);
            rlNoData.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
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
            if (CommonMethods.isNetworkAvailable(AddToCartActivity.this)) {
                String items = "";
                String prices = "";
                String quantity = "";
                if (productBOArrayList.size() > 0) {
                    StringBuilder items_builder = new StringBuilder();
                    StringBuilder price_builder = new StringBuilder();
                    StringBuilder quantity_builder = new StringBuilder();
                    for (ProductBO productBO : productBOArrayList) {
                        items_builder.append(productBO.getId()).append(",");
                        price_builder.append(productBO.getPrice()).append(",");
                        quantity_builder.append(productBO.getQuantity()).append(",");
                    }
                    items = items_builder.deleteCharAt(items_builder.length() - 1).toString();
                    prices = price_builder.deleteCharAt(price_builder.length() - 1).toString();
                    quantity = quantity_builder.deleteCharAt(quantity_builder.length() - 1).toString();
                }
                //Create order of user...
                createOrder(items, prices, quantity);
            } else {
                Toast.makeText(AddToCartActivity.this, AllKeys.NO_INTERNET_AVAILABLE, Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btn_shopping_now) {
            startActivity(new Intent(AddToCartActivity.this, DashBoardActivity.class));
            finish();
        }
    }

    private void createOrder(String items, String prices, String quantity) {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Please wait....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        Map<String, String> params = new HashMap<>();
        params.put("userid", "464");
        params.put("items", items);
        params.put("itemprices", prices);
        params.put("quantity", quantity);
        params.put("total", tvTotalAmount.getText().toString());

        Log.e(TAG, "createOrder: params" + params);

        bookSelling.getApiRequestHelper().createOrder(params, new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                CreateOrderResponse createOrderResponse = (CreateOrderResponse) object;
                Log.e(TAG, "onSuccess: " + createOrderResponse.getResponsecode());
                //Log.e(TAG, "onSuccess: " + createOrderResponse.getMessage());
                //Log.e(TAG, "onSuccess: " + createOrderResponse.getTransactionId());
                progress.dismiss();
                if (createOrderResponse.getResponsecode() == 200) {
                    Toast.makeText(AddToCartActivity.this, createOrderResponse.getMessage() + "\n" + createOrderResponse.getTransactionId(), Toast.LENGTH_SHORT).show();

                    //If all successfull then it will go for payment gateway....
                    startPaymentGateway(String.valueOf(createOrderResponse.getTransactionId()));

                } else {
                    Toast.makeText(AddToCartActivity.this, createOrderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(AddToCartActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPaymentGateway(String transactionID) {
        Intent intent = new Intent(AddToCartActivity.this, CCAvenueWebViewActivity.class);
        intent.putExtra(AvenuesParams.ACCESS_CODE, BuildConfig.ACCESS_CODE);
        intent.putExtra(AvenuesParams.MERCHANT_ID, BuildConfig.MERCHANT_ID);
        intent.putExtra(AvenuesParams.ORDER_ID, transactionID);
        intent.putExtra(AvenuesParams.CURRENCY, "INR");
        intent.putExtra(AvenuesParams.AMOUNT, Utils.format2Dec(Double.parseDouble(tvTotalAmount.getText().toString())) + "");
        intent.putExtra(AvenuesParams.REDIRECT_URL, BuildConfig.REDIRECT_URL);
        intent.putExtra(AvenuesParams.CANCEL_URL, BuildConfig.CANCEL_URL);
        intent.putExtra(AvenuesParams.RSA_KEY_URL, BuildConfig.RSA_KEY_URL);
        intent.putExtra(AvenuesParams.BILLING_COUNTRY, "India");
        intent.putExtra(AvenuesParams.BILLING_STATE, "Maharashtra");
        intent.putExtra(AvenuesParams.BILLING_CITY, "Pune");
        intent.putExtra(AvenuesParams.BILLING_ZIP, "411001");
        /*intent.putExtra(AvenuesParams.BILLING_NAME, getName(data));
        intent.putExtra(AvenuesParams.BILLING_ADDRESS, data.getAddress());
        intent.putExtra(AvenuesParams.BILLING_TEL, data.getMobile());
        intent.putExtra(AvenuesParams.BILLING_EMAIL, data.getEmail());
        intent.putExtra(AvenuesParams.DELIVERY_NAME, getName(data));
        intent.putExtra(AvenuesParams.DELIVERY_ADDRESS, data.getAddress());
        */intent.putExtra(AvenuesParams.DELIVERY_CITY, "Pune");
        intent.putExtra(AvenuesParams.DELIVERY_STATE, "Maharashtra");
        intent.putExtra(AvenuesParams.DELIVERY_ZIP, "411001");
        intent.putExtra(AvenuesParams.DELIVERY_COUNTRY, "India");
        //intent.putExtra(AvenuesParams.DELIVERY_TEL, data.getMobile());
        intent.putExtra(AvenuesParams.BILLING_NOTES, "");
        startActivityForResult(intent, 121);
    }

    //Setting text to TextView when deleting or updating the recycler view..
    public void getTotal(int total) {
        tvTotalAmount.setText("" + total);
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