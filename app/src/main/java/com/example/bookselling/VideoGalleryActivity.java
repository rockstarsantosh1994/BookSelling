package com.example.bookselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bookselling.adapter.SuccessStoryAdapter;
import com.example.bookselling.adapter.VideoGalleryAdapter;
import com.example.bookselling.model.story.SuccessStoryResponse;
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

public class VideoGalleryActivity extends AppCompatActivity {

    @BindView(R.id.rv_video_gallery)
    RecyclerView rvVideoGallery;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private BookSelling bookSelling;
    private Map<String, ArrayList<VideoGalleryBO>> videoGalleryMap=new HashMap<>();
    private ArrayList<VideoGalleryBO> temp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);
        bookSelling=(BookSelling)getApplication();
        ButterKnife.bind(this);

        //basic intilaisation...
        initViews();

        //load video gallery data...
        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Video Gallery");
        toolbar.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        GridLayoutManager linearLayout=new GridLayoutManager(VideoGalleryActivity.this,2);
        rvVideoGallery.setLayoutManager(linearLayout);
    }

    private void loadData(){
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Please wait....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        bookSelling.getApiRequestHelper().getvideogallery(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                VideoGalleryResponse videoGalleryResponse = (VideoGalleryResponse) object;
                //Log.e(TAG, "onSuccess: " + loginResponse.getResponsecode());
                //  Log.e(TAG, "onSuccess: " + loginResponse.getMessage());
                progress.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                if (videoGalleryResponse.getResponsecode() == 200) {
                    Toast.makeText(VideoGalleryActivity.this,videoGalleryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    //Attach data to custom recycler view..
                    if(videoGalleryResponse.getData().size()>0){

                        for(VideoGalleryBO temp : videoGalleryResponse.getData())
                        {
                            if (videoGalleryMap.containsKey(temp.getCategoryTitle().trim()))
                            {
                                temp1 = videoGalleryMap.get(temp.getCategoryTitle().trim());
                                temp1.add(temp);
                            }
                            else
                            {
                                temp1 = new ArrayList();
                                temp1.add(temp);
                                videoGalleryMap.put(temp.getCategoryTitle().trim(),temp1);
                            }
                        }
                        VideoGalleryAdapter videoGalleryActivity=new VideoGalleryAdapter(VideoGalleryActivity.this,videoGalleryMap.keySet().toArray(),videoGalleryMap);
                        rvVideoGallery.setAdapter(videoGalleryActivity);
                    }
                } else {
                    Toast.makeText(VideoGalleryActivity.this,videoGalleryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(VideoGalleryActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
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