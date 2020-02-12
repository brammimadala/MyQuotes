package com.lasys.app.quotes.network;

import com.lasys.app.quotes.model.authors.AuthorsData;
import com.lasys.app.quotes.model.categories.CategoriesData;
import com.lasys.app.quotes.model.dashboard.DashboardData;
import com.lasys.app.quotes.model.myfav.FavDetails;
import com.lasys.app.quotes.model.myfav.FavResponse;
import com.lasys.app.quotes.model.quotes.Quote;
import com.lasys.app.quotes.model.usercreate.UserCreate;
import com.lasys.app.quotes.model.usercreate.UserDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {
    @GET("mobile/dashboard_items")
    Call<DashboardData> getDashBoardResponse();

    @GET("mobile/categories")
    Call<CategoriesData> getCategories();

    @GET("mobile/authors")
    Call<AuthorsData> getAuthors();

    @GET("mobile/quotes")
    Call<Quote> getQuotes();

    @GET("mobile/telugu_quotes")
    Call<Quote> getTeluguQuotes();

    @GET("mobile/author_quotes/{id}")
    Call<Quote> getAuthQuotes(@Path("id") String authorId);

    @GET("mobile/category_quotes/{id}")
    Call<Quote> getCategoryQuotes(@Path("id") String categoryId);


    @GET("mobile/my_fav/{id}")
    Call<Quote> getMyFavourite(@Path("id") int user_id);


    @GET("mobile/quote_of_the_day")
    Call<Quote> getDayQuote();

  /*  @FormUrlEncoded
    @POST("mobile/users_create")
    Call<UserCreate> userCreate(@Field("token") String token,
                                @Field("device_name") String device_name,         //HUAWEI
                                @Field("device_model") String device_model ,      //BND-AL10
                                @Field("device_version") String device_version);  //SDK 26, RELEASE 8.0.0*/

    /*@FormUrlEncoded
    @POST("mobile/fav_create")
    Call<Response> createFavourite(@Field("user_id") int userId,
                                   @Field("unique_id") String uniqueId,
                                   @Field("quote_id") String quoteId);*/


    @POST("mobile/users_create")
    Call<UserCreate> userCreate(@Body UserDetails userDetails);

    @POST("mobile/fav_create")
    Call<FavResponse> createFavourite(@Body FavDetails favDetails);


}
