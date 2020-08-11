package com.example.bookselling.model.myorder;

import android.os.Parcel;
import android.os.Parcelable;

public class MyItemsBO implements Parcelable {

    private int productid;
    private int price;
    private int quantity;
    private String title;
    private String details;

    protected MyItemsBO(Parcel in) {
        productid = in.readInt();
        price = in.readInt();
        quantity = in.readInt();
        title = in.readString();
        details = in.readString();
    }

    public static final Creator<MyItemsBO> CREATOR = new Creator<MyItemsBO>() {
        @Override
        public MyItemsBO createFromParcel(Parcel in) {
            return new MyItemsBO(in);
        }

        @Override
        public MyItemsBO[] newArray(int size) {
            return new MyItemsBO[size];
        }
    };

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productid);
        dest.writeInt(price);
        dest.writeInt(quantity);
        dest.writeString(title);
        dest.writeString(details);
    }
}
