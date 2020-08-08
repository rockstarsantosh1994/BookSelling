package com.example.bookselling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.bookselling.adapter.BookScreenAdapter;
import com.example.bookselling.model.product.ProductResponse;
import com.example.bookselling.services.ApiRequestHelper;
import com.example.bookselling.services.BookSelling;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rv_book)
    RecyclerView rvBook;
    @BindView(R.id.btn_video_gallery)
    AppCompatButton btnVideoGallery;
    @BindView(R.id.btn_success_story)
    AppCompatButton btnSuccessStory;

    private BookSelling bookSelling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bookSelling=(BookSelling) getApplication();

        //basic intialisation...
        initViews();

        //load book data and append to recycler view....
        loadData();
    }

    private void initViews(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
        rvBook.setLayoutManager(linearLayoutManager);

        //click listeners
        btnVideoGallery.setOnClickListener(this);
        btnSuccessStory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_video_gallery:
                startActivity(new Intent(MainActivity.this,VideoGalleryActivity.class));
                break;

            case R.id.btn_success_story:
                startActivity(new Intent(MainActivity.this,SuccessStoryActivity.class).putExtra("type","success_story"));
                break;
        }
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