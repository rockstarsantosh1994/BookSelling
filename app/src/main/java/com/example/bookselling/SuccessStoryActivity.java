package com.example.bookselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bookselling.adapter.SuccessStoryAdapter;
import com.example.bookselling.adapter.VideoGalleryDataAdapter;
import com.example.bookselling.model.story.SuccessStoryResponse;
import com.example.bookselling.services.ApiRequestHelper;
import com.example.bookselling.services.BookSelling;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuccessStoryActivity extends AppCompatActivity {

    @BindView(R.id.rv_success_story)
    RecyclerView rvSuccessStory;

    private BookSelling bookSelling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_story);
        ButterKnife.bind(this);
        bookSelling=(BookSelling)getApplication();

        //basic intialisation...
        initViews();

        if(getIntent().getStringExtra("type").equals("video_gallery")){
            if(getIntent().getParcelableArrayListExtra("videogallerybo")!=null){
                VideoGalleryDataAdapter videoGalleryDataAdapter= new VideoGalleryDataAdapter(SuccessStoryActivity.this,getIntent().getParcelableArrayListExtra("videogallerybo"));
                rvSuccessStory.setAdapter(videoGalleryDataAdapter);
            }
        }else{
            //load success story data and append to recyclerview..
            loadData();
        }
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Success Story");
        toolbar.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        LinearLayoutManager linearLayout=new LinearLayoutManager(SuccessStoryActivity.this);
        rvSuccessStory.setLayoutManager(linearLayout);
    }

    private void loadData(){
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Please wait....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        bookSelling.getApiRequestHelper().getsuccessstory(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                SuccessStoryResponse successStoryResponse = (SuccessStoryResponse) object;
                //Log.e(TAG, "onSuccess: " + loginResponse.getResponsecode());
                //  Log.e(TAG, "onSuccess: " + loginResponse.getMessage());
                progress.dismiss();
                if (successStoryResponse.getResponsecode() == 200) {
                    Toast.makeText(SuccessStoryActivity.this,successStoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    //Attach data to custom recycler view..
                    if(successStoryResponse.getData().size()>0){
                        SuccessStoryAdapter successStoryAdapter=new SuccessStoryAdapter(SuccessStoryActivity.this,successStoryResponse.getData());
                        rvSuccessStory.setAdapter(successStoryAdapter);
                    }
                } else {
                    Toast.makeText(SuccessStoryActivity.this,successStoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(SuccessStoryActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
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