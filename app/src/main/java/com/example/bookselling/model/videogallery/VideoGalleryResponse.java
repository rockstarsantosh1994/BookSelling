package com.example.bookselling.model.videogallery;

import java.util.ArrayList;

public class VideoGalleryResponse {
    private int Responsecode;
    private String Message;
    private ArrayList<VideoGalleryBO> Data;

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<VideoGalleryBO> getData() {
        if (this.Data==null){
            this.Data=new ArrayList<VideoGalleryBO>();
        }
        return Data;
    }

    public void setData(ArrayList<VideoGalleryBO> data) {
        Data = data;
    }
}
