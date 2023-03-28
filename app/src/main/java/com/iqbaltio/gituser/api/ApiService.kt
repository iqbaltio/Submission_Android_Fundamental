package com.iqbaltio.gituser.api

import com.iqbaltio.gituser.DetailResponse
import com.iqbaltio.gituser.ItemsItem
import com.iqbaltio.gituser.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("search/users")
    fun getUser(
        @Query("q") login: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}