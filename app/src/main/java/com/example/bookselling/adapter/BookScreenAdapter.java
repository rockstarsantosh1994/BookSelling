package com.example.bookselling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookselling.R;
import com.example.bookselling.model.product.ProductBO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookScreenAdapter extends RecyclerView.Adapter<BookScreenAdapter.BookScreenViewHolder>{

    private Context context;
    private ArrayList<ProductBO> productBOArrayList;
    private final int[] backgroundColors = {R.color.grey100,R.color.grey50};

    public BookScreenAdapter(Context context, ArrayList<ProductBO> productBOArrayList) {
        this.context = context;
        this.productBOArrayList = productBOArrayList;
    }

    @NonNull
    @Override
    public BookScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_book_screen,parent,false);
        return new BookScreenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookScreenViewHolder holder, int position) {
        int bgColor = ContextCompat.getColor(context, backgroundColors[position % 2]);
        holder.cardView.setCardBackgroundColor(bgColor);

        Glide.with(context).load(productBOArrayList.get(position).getCoverPhoto()).into(holder.ivProductImage);

        holder.tvProductTitle.setText(productBOArrayList.get(position).getTitle());
        holder.tvProductPrice.setText("₹" +productBOArrayList.get(position).getPrice() + " /-");
        holder.tvProductDescription.setText(productBOArrayList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return productBOArrayList.size();
    }

    public class BookScreenViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_product_img)
        ImageView ivProductImage;
        @BindView(R.id.tv_product_title)
        TextView tvProductTitle;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_product_description)
        TextView tvProductDescription;
        @BindView(R.id.ll_product_click)
        LinearLayout llProductClick;
        @BindView(R.id.cardview)
        CardView cardView;

        public BookScreenViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
