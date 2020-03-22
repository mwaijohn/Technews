package com.honetware.technology.technews.controllers;

import com.honetware.technology.technews.model.StatusPayload;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetArticles {
    @GET("top-headlines")
    Call<StatusPayload> getArticleResponse(@Query("sources") String sources,
                                           @Query("pageSize") int pageSize,
                                           @Query("language") String language,
                                           @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<StatusPayload> getCategoryResponse(@Query("category") String category,
                                            @Query("pageSize") int pageSize,
                                            @Query("language") String language,
                                            @Query("sortBy") String sortBy ,
                                            @Query("apiKey") String apiKey);
}
