package com.example.bookselling.model.story;

import java.util.ArrayList;

public class SuccessStoryResponse {
    private int Responsecode;
    private String Message;
    private ArrayList<SuccessStoryBO> Data;

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

    public ArrayList<SuccessStoryBO> getData() {
        if(this.Data==null){
            this.Data=new ArrayList<SuccessStoryBO>();
        }
        return Data;
    }

    public void setData(ArrayList<SuccessStoryBO> data) {
        Data = data;
    }
}
