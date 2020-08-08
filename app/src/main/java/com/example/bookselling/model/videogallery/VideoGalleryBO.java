package com.example.bookselling.model.videogallery;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoGalleryBO implements Parcelable {
    private  int videoId;
    private  int categoryId;
    private  String title;
    private  String url;
    private  int sequenceNo;
    private  int isActive;
    private String categoryTitle;

    protected VideoGalleryBO(Parcel in) {
        videoId = in.readInt();
        categoryId = in.readInt();
        title = in.readString();
        url = in.readString();
        sequenceNo = in.readInt();
        isActive = in.readInt();
        categoryTitle = in.readString();
    }

    public static final Creator<VideoGalleryBO> CREATOR = new Creator<VideoGalleryBO>() {
        @Override
        public VideoGalleryBO createFromParcel(Parcel in) {
            return new VideoGalleryBO(in);
        }

        @Override
        public VideoGalleryBO[] newArray(int size) {
            return new VideoGalleryBO[size];
        }
    };

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(videoId);
        dest.writeInt(categoryId);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeInt(sequenceNo);
        dest.writeInt(isActive);
        dest.writeString(categoryTitle);
    }
}
