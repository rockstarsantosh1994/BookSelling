package com.example.bookselling.services;

import android.app.Application;
import android.content.Context;

public class BookSelling extends Application {

    private ApiRequestHelper apiRequestHelper;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        doInit();

    }

    private void doInit() {

        this.apiRequestHelper = ApiRequestHelper.init(this);
    }

    public synchronized ApiRequestHelper getApiRequestHelper() {
        return apiRequestHelper;
    }
}

