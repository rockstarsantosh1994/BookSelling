package com.example.bookselling.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookselling.AddToCartActivity;
import com.example.bookselling.R;
import com.example.bookselling.model.product.ProductBO;
import com.shawnlin.numberpicker.NumberPicker;
import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.AddToCartViewHolder>{

    private Context context;
    private ArrayList<ProductBO> productBOArrayList;
    int sum = 0;
    private AddToCartActivity addToCartActivity;

    public AddToCartAdapter(Context context, ArrayList<ProductBO> productBOArrayList,AddToCartActivity addToCartActivity) {
        this.context = context;
        this.productBOArrayList = productBOArrayList;
        this.addToCartActivity=addToCartActivity;
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
        if(productBOArrayList.get(position).getCoverPhoto()!=null){
            Glide.with(context).load(productBOArrayList.get(position).getCoverPhoto()).into(holder.ivProductImage);
        }

        holder.tvProductTitle.setText(productBOArrayList.get(position).getTitle());
        sum = productBOArrayList.get(position).getPrice()* productBOArrayList.get(position).getQuantity();
        holder.tvProductPrice.setText("₹" + sum + " /-");
        holder.tvProductDescription.setText(productBOArrayList.get(position).getDetails());
        holder.numberPicker.setValue(productBOArrayList.get(position).getQuantity());

        holder.ivRemove.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.app_name)
                    .setMessage("Are you sure want to remove product?")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.

                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Continue with delete operation
                        productBOArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,productBOArrayList.size());
                        Paper.book().write("product_cart",productBOArrayList);
                        try{
                            int sum = 0;
                            for(int i = 0; i <productBOArrayList.size(); i++)
                                sum += productBOArrayList.get(i).getProductTotal();
                            addToCartActivity.getTotal(sum);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                       /* Intent intent=new Intent(context,AddToCartActivity.class);
                        context.startActivity(intent);
                        ((AppCompatActivity)context).finish();*/
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        // OnValueChangeListener
        holder.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                sum = newVal * productBOArrayList.get(position).getPrice();

                productBOArrayList.set(position,new ProductBO(
                        productBOArrayList.get(position).getId(),
                        productBOArrayList.get(position).getTitle(),
                        productBOArrayList.get(position).getDetails(),
                        productBOArrayList.get(position).getPrice(),
                        productBOArrayList.get(position).getCoverPhoto(),
                        newVal,
                        productBOArrayList.get(position).getIsActive(),
                        sum));

                notifyItemChanged(position);
                Paper.book().write("product_cart",productBOArrayList);

                int sum = 0;
                for(int i = 0; i < productBOArrayList.size(); i++)
                    sum += productBOArrayList.get(i).getProductTotal();
                addToCartActivity.getTotal(sum);
            }
        });
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
