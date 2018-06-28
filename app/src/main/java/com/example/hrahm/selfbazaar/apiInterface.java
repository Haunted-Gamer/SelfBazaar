package com.example.hrahm.selfbazaar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface apiInterface {
    @GET("searchItem.php")
    Call<List<items>> searchItem (@Query("search") String SEARCH);

    @FormUrlEncoded
    @POST("create.php")
    Call<JsonObject> registration(@Field("name") String Name, @Field("email") String Email, @Field("pass") String Password, @Field("dob") String DOB, @Field("phone") String phoneNo, @Field("title") String Title, @Field("image") String image);

    @GET("getTotalUserNumber.php")
    Call<JsonArray> getNumber();


    @GET("login.php")
    Call<List <users>> login (@Query("email") String EMAIL, @Query("pass") String PASS);

    @FormUrlEncoded
    @POST("additem.php")
    Call<JsonObject> itemAdd(@Field("name") String Name, @Field("desc") String Desc, @Field("price") String Price, @Field("photo") String Photo, @Field("location") String Location, @Field("category") String Category,@Field("uid") String Uid);
}
