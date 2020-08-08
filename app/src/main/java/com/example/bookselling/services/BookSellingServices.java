package com.example.bookselling.services;

import com.example.bookselling.model.product.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookSellingServices {

    @GET("products.php")
    Call<ProductResponse> getProductData();
}