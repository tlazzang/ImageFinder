package com.example.shim.stunitas.Network;

import com.example.shim.stunitas.Model.KaKaoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface KaKaoApiService {
    @Headers({
            "Authorization:KakaoAK 1bc00b1181d12c5bc437fe898645f1b8",
    })
    @GET("v2/search/image")
    Call<KaKaoResponse> getImage(@Query("query") String query);

    @Headers({
            "Authorization:KakaoAK 1bc00b1181d12c5bc437fe898645f1b8",
    })
    @GET("v2/search/image")
    Call<KaKaoResponse> getImage(@Query("query") String query, @Query("page") int page, @Query("size") int size);

    @Headers({
            "Authorization:KakaoAK 1bc00b1181d12c5bc437fe898645f1b8",
    })
    @GET("v2/search/image")
    Call<KaKaoResponse> getImage(@Query("query") String query, @Query("page") int page);
}
