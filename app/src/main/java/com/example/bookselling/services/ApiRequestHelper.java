package com.example.bookselling.services;

import android.text.Html;

import com.example.bookselling.model.myorder.MyOrderResponse;
import com.example.bookselling.model.product.ProductResponse;
import com.example.bookselling.model.story.SuccessStoryResponse;
import com.example.bookselling.model.videogallery.VideoGalleryResponse;
import com.example.bookselling.utils.AllKeys;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestHelper {

    public interface OnRequestComplete {
        void onSuccess(Object object);

        void onFailure(String apiResponse);
    }

    private static ApiRequestHelper instance;
    private BookSelling application;
    private BookSellingServices banyanBazarServices;
    static Gson gson;


    public static synchronized ApiRequestHelper init(BookSelling application) {
        if (null == instance) {
            instance = new ApiRequestHelper();
            instance.setApplication(application);
            gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                    .setExclusionStrategies(new ExclusionStrategy() {
//                        @Override
//                        public boolean shouldSkipField(FieldAttributes f) {
//                            return f.getDeclaringClass().equals(RealmObject.class);
//                        }
//
//                        @Override
//                        public boolean shouldSkipClass(Class<?> clazz) {
//                            return false;
//                        }
//                    })
                    .create();
            instance.createRestAdapter();
        }
        return instance;
    }

    public void getproductdata(final OnRequestComplete onRequestComplete) {
        Call<ProductResponse> call = banyanBazarServices.getProductData();
        product_api(onRequestComplete, call);
    }

    public void getsuccessstory(final OnRequestComplete onRequestComplete) {
        Call<SuccessStoryResponse> call = banyanBazarServices.getSuccessStory();
        success_api(onRequestComplete, call);
    }

    public void getvideogallery(final OnRequestComplete onRequestComplete) {
        Call<VideoGalleryResponse> call = banyanBazarServices.getVideoGallery();
        video_gallery_api(onRequestComplete, call);
    }

    public void myOrder(Map<String,String> params, final OnRequestComplete onRequestComplete) {
        Call<MyOrderResponse> call = banyanBazarServices.myOrder(params);
        my_order_api(onRequestComplete, call);
    }

    private void my_order_api(final OnRequestComplete onRequestComplete, Call<MyOrderResponse> call) {
        call.enqueue(new Callback<MyOrderResponse>() {
            @Override
            public void onResponse(Call<MyOrderResponse> call, Response<MyOrderResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyOrderResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }


    private void product_api(final OnRequestComplete onRequestComplete, Call<ProductResponse> call) {
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void success_api(final OnRequestComplete onRequestComplete, Call<SuccessStoryResponse> call) {
        call.enqueue(new Callback<SuccessStoryResponse>() {
            @Override
            public void onResponse(Call<SuccessStoryResponse> call, Response<SuccessStoryResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessStoryResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void video_gallery_api(final OnRequestComplete onRequestComplete, Call<VideoGalleryResponse> call) {
        call.enqueue(new Callback<VideoGalleryResponse>() {
            @Override
            public void onResponse(Call<VideoGalleryResponse> call, Response<VideoGalleryResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoGalleryResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void handle_fail_response(Throwable t, OnRequestComplete onRequestComplete) {
        if (t.getMessage() != null) {
            if (t.getMessage().contains("Unable to resolve host")) {
                onRequestComplete.onFailure(AllKeys.NO_INTERNET_AVAILABLE);
            } else {
                onRequestComplete.onFailure(Html.fromHtml(t.getLocalizedMessage()) + "");
            }
        } else
            onRequestComplete.onFailure(AllKeys.UNPROPER_RESPONSE);
    }

    private static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    public static class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    /**
     * REST Adapter Configuration
     */
    private void createRestAdapter() {
        //ObjectMapper objectMapper = new ObjectMapper();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.interceptors().add(logging);
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(AllKeys.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.client(httpClient.build()).build();
        banyanBazarServices = retrofit.create(BookSellingServices.class);
    }

    /**
     * End REST Adapter Configuration
     */


    public BookSelling getApplication() {
        return application;
    }

    public void setApplication(BookSelling application) {
        this.application = application;
    }

}
