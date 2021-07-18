package com.yoochangwonspro.myminiproject

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ): Call<User>

    @POST("/user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username")username: String,
        @Field("password")password1: String
    ) : Call<User>

    @GET("/instagram/post/list/all/")
    fun getAllPosts(): Call<ArrayList<Post>>
}