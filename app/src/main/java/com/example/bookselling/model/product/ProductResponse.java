package com.example.bookselling.model.product;

import java.util.ArrayList;

public class ProductResponse {
    private int Responsecode;
    private String Message;
    private ArrayList<ProductBO> Data;

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

    public ArrayList<ProductBO> getData() {
        if(this.Data==null){
            this.Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<ProductBO> data) {
        Data = data;
    }
}
