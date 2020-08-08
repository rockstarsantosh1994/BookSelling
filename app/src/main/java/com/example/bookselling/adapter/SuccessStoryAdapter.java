package com.example.bookselling.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookselling.R;
import com.example.bookselling.model.story.SuccessStoryBO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuccessStoryAdapter extends RecyclerView.Adapter<SuccessStoryAdapter.SuccessStoryViewHolder>{

    private Context context;
    private ArrayList<SuccessStoryBO> successStoryBOArrayList;

    public SuccessStoryAdapter(Context context, ArrayList<SuccessStoryBO> successStoryBOArrayList) {
        this.context = context;
        this.successStoryBOArrayList = successStoryBOArrayList;
    }

    @NonNull
    @Override
    public SuccessStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_success_story,parent,false);
        return  new SuccessStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuccessStoryViewHolder holder, int position) {

        holder.tvProductTitle.setText(successStoryBOArrayList.get(position).getTitle());
        holder.tvProductDescription.setText(successStoryBOArrayList.get(position).getDetails());

        Glide.with(context).load("https://img.youtube.com/vi/"+(successStoryBOArrayList.get(position).getVideoUrl())+"/0.jpg").into(holder.ivBanner);

        holder.tvViewVideo.setOnClickListener(v -> {
            String url = successStoryBOArrayList.get(position).getVideoUrl();
                   try {
                       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + url));
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       context.startActivity(intent);
                  } catch (ActivityNotFoundException e) {
                     // youtube is not installed.Will be opened in other available appsIntent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=" + url));
                       Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=" + url));
                       context.startActivity(i);
                   }
        });
    }

    @Override
    public int getItemCount() {
        return successStoryBOArrayList.size();
    }

    public class SuccessStoryViewHolder extends RecyclerView.ViewHolder{

         @BindView(R.id.tv_product_title)
         TextView tvProductTitle;
         @BindView(R.id.tv_product_description)
         TextView tvProductDescription;
         @BindView(R.id.tv_view_video)
         TextView tvViewVideo;
         @BindView(R.id.iv_banner)
         ImageView ivBanner;

         public SuccessStoryViewHolder(@NonNull View itemView) {
             super(itemView);
             ButterKnife.bind(this,itemView);
         }
     }
}
