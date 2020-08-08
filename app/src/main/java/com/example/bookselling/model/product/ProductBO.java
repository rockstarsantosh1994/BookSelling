package com.example.bookselling.model.product;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductBO implements Parcelable {
    private int id;
    private String title;
    private String details;
    private int price;
    private String coverPhoto;
    private String creationDateTime;
    private int isActive;

    protected ProductBO(Parcel in) {
        id = in.readInt();
        title = in.readString();
        details = in.readString();
        price = in.readInt();
        coverPhoto = in.readString();
        creationDateTime = in.readString();
        isActive = in.readInt();
    }

    public static final Creator<ProductBO> CREATOR = new Creator<ProductBO>() {
        @Override
        public ProductBO createFromParcel(Parcel in) {
            return new ProductBO(in);
        }

        @Override
        public ProductBO[] newArray(int size) {
            return new ProductBO[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(details);
        dest.writeInt(price);
        dest.writeString(coverPhoto);
        dest.writeString(creationDateTime);
        dest.writeInt(isActive);
    }
}
