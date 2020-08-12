package com.example.bookselling.model.myorder;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.bookselling.model.product.ProductBO;

import java.util.ArrayList;

public class MyOrderBO implements Parcelable {
    private int id;
    private int userId;
    private int totalPrice;
    private String orderDateTime;
    private int isSucceed;
    private String orderPic;
    private ArrayList<ProductBO> items;

    protected MyOrderBO(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        totalPrice = in.readInt();
        orderDateTime = in.readString();
        orderPic = in.readString();
        isSucceed = in.readInt();
        items = in.createTypedArrayList(ProductBO.CREATOR);
    }

    public static final Creator<MyOrderBO> CREATOR = new Creator<MyOrderBO>() {
        @Override
        public MyOrderBO createFromParcel(Parcel in) {
            return new MyOrderBO(in);
        }

        @Override
        public MyOrderBO[] newArray(int size) {
            return new MyOrderBO[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public int getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(int isSucceed) {
        this.isSucceed = isSucceed;
    }

    public String getOrderPic() {
        return orderPic;
    }

    public void setOrderPic(String orderPic) {
        this.orderPic = orderPic;
    }

    public ArrayList<ProductBO> getItems() {
        if(this.items==null){
            this.items=new ArrayList<ProductBO>();
        }
        return items;
    }

    public void setItems(ArrayList<ProductBO> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(totalPrice);
        dest.writeString(orderDateTime);
        dest.writeString(orderPic);
        dest.writeInt(isSucceed);
        dest.writeTypedList(items);
    }
}
