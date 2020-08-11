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
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookselling.R;
import com.example.bookselling.model.product.ProductBO;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.AddToCartViewHolder>{

    private Context context;
    private ArrayList<ProductBO> productBOArrayList;

    public AddToCartAdapter(Context context, ArrayList<ProductBO> productBOArrayList) {
        this.context = context;
        this.productBOArrayList = productBOArrayList;
    }

    @NonNull
    @Override
    public AddToCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_add_to_cart,parent,false);
        return new AddToCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddToCartViewHolder holder, int position) {
      //  Glide.with(context).load(AllKeys.PRODUCT_IMG_URL + addToCartActivity.productsBOMap.get(stringArray[position]).getProductid() + ".jpg").into(holder.ivProductImage);
        holder.tvProductTitle.setText("");
        holder.tvProductPrice.setText("₹" +"" + " /-");
        holder.tvProductDescription.setText("");
        holder.numberPicker.setValue(1);
    }

    @Override
    public int getItemCount() {
        return productBOArrayList.size();
    }

    public class AddToCartViewHolder extends RecyclerView.ViewHolder{

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
        @BindView(R.id.iv_remove)
        ImageView ivRemove;
        @BindView(R.id.np_picker)
        NumberPicker numberPicker;
        @BindView(R.id.cardview)
        CardView cardView;

        public AddToCartViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            // Set wrap selector wheel
            numberPicker.setWrapSelectorWheel(true);
        }
    }
}
