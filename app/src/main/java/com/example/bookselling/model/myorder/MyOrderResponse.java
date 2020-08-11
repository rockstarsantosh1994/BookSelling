package com.example.bookselling.model.myorder;

import java.util.ArrayList;

public class MyOrderResponse {

    private int Responsecode;
    private String Message;
    private ArrayList<MyOrderBO> Data;

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

    public ArrayList<MyOrderBO> getData() {
        if(this.Data==null){
            this.Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<MyOrderBO> data) {
        Data = data;
    }
}
