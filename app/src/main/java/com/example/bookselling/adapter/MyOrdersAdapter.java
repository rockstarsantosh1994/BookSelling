package com.example.bookselling.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookselling.BookInfoActivity;
import com.example.bookselling.MyOrdersDetailsActivity;
import com.example.bookselling.R;
import com.example.bookselling.model.myorder.MyOrderBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder>{

    private Context context;
    private ArrayList<MyOrderBO> myOrderBOArrayList;

    public MyOrdersAdapter(Context context, ArrayList<MyOrderBO> myOrderBOArrayList) {
        this.context = context;
        this.myOrderBOArrayList = myOrderBOArrayList;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_my_orders,parent,false);
        return new MyOrdersViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position) {

         if(myOrderBOArrayList.get(position).getIsSucceed()==1){
             holder.tvOrderStatus.setText("Order Confirmed");
         }else{
             holder.tvOrderStatus.setText("Order Processing");
         }

         holder.tvOrderPrice.setText("₹"+myOrderBOArrayList.get(position).getTotalPrice()+" /-");

        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("EEE dd MMM yy");
        Date date = null;
        try {
            date = spf.parse(myOrderBOArrayList.get(position).getOrderDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strDate=format1.format(date);
        holder.tvOrderDate.setText("Ordered on:- "+strDate);

        holder.cvMyOrders.setOnClickListener(v -> {
            if(myOrderBOArrayList.get(position).getItems().size()>0){
                Intent intent=new Intent(context, MyOrdersDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("my_order",myOrderBOArrayList.get(position));
                context.startActivity(intent);
            }else{
                Toast.makeText(context, "No products available!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return myOrderBOArrayList.size();
    }

    public class MyOrdersViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_order_date)
        TextView tvOrderDate;
        @BindView(R.id.tv_order_price)
        TextView tvOrderPrice;
        @BindView(R.id.cv_my_orders)
        CardView cvMyOrders;

        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
