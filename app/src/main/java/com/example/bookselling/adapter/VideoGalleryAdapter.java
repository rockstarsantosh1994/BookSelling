package com.example.bookselling.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookselling.R;
import com.example.bookselling.SuccessStoryActivity;
import com.example.bookselling.model.videogallery.VideoGalleryBO;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoGalleryAdapter extends RecyclerView.Adapter<VideoGalleryAdapter.VideoGalleryViewHolder>{

    private Context context;
    private Object[] stringArray;
    private Map<String,ArrayList<VideoGalleryBO>> videoGalleryBOMap;

    public VideoGalleryAdapter(Context context, Object[] stringArray, Map<String, ArrayList<VideoGalleryBO>> videoGalleryMap) {
        this.context = context;
        this.stringArray = stringArray;
        this.videoGalleryBOMap=videoGalleryMap;
    }

    @NonNull
    @Override
    public VideoGalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_video_gallery,parent,false);
        return new VideoGalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoGalleryViewHolder holder, int position) {
        holder.tvCategoryName.setText(""+stringArray[position]);

        holder.cardView.setOnClickListener(v -> {
            Intent intent=new Intent(context, SuccessStoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("videogallerybo",videoGalleryBOMap.get(stringArray[position]));
            intent.putExtra("type","video_gallery");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return stringArray.length;
    }

    public class VideoGalleryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_category_name)
        TextView tvCategoryName;
        @BindView(R.id.ll_click_category)
        CardView cardView;

        public VideoGalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
