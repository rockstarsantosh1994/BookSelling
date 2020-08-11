package com.example.bookselling.model.myorder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MyOrderBO implements Parcelable {
    private int id;
    private int userId;
    private int totalPrice;
    private String orderDateTime;
    private int isSucceed;
    private ArrayList<MyItemsBO> items;

    protected MyOrderBO(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        totalPrice = in.readInt();
        orderDateTime = in.readString();
        isSucceed = in.readInt();
        items = in.createTypedArrayList(MyItemsBO.CREATOR);
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

    public ArrayList<MyItemsBO> getItems() {
        if(this.items==null){
            this.items=new ArrayList<MyItemsBO>();
        }
        return items;
    }

    public void setItems(ArrayList<MyItemsBO> items) {
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
        dest.writeInt(isSucceed);
        dest.writeTypedList(items);
    }
}
