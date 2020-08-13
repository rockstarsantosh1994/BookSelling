package com.example.bookselling.services;

import com.example.bookselling.model.CreateOrderResponse;
import com.example.bookselling.model.myorder.MyOrderResponse;
import com.example.bookselling.model.product.ProductResponse;
import com.example.bookselling.model.story.SuccessStoryResponse;
import com.example.bookselling.model.videogallery.VideoGalleryResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BookSellingServices {

    @GET("products.php")
    Call<ProductResponse> getProductData();

    @GET("successtory.php")
    Call<SuccessStoryResponse> getSuccessStory();

    @GET("videogallery.php")
    Call<VideoGalleryResponse> getVideoGallery();

    @FormUrlEncoded
    @POST("myorders.php")
    Call<MyOrderResponse> myOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("createorder.php")
    Call<CreateOrderResponse> createOrder(@FieldMap Map<String, String> params);
}