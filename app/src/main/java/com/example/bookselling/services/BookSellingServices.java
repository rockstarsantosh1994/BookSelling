package com.example.bookselling.services;

import com.example.bookselling.model.product.ProductResponse;
import com.example.bookselling.model.story.SuccessStoryResponse;
import com.example.bookselling.model.videogallery.VideoGalleryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookSellingServices {

    @GET("products.php")
    Call<ProductResponse> getProductData();

    @GET("successtory.php")
    Call<SuccessStoryResponse> getSuccessStory();

    @GET("videogallery.php")
    Call<VideoGalleryResponse> getVideoGallery();
}