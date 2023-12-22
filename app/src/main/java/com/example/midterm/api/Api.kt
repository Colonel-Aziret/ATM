package com.example.midterm.api

import com.example.midterm.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("authenticate")
    suspend fun authUser(
            @Body user: User
    ): Response<Balance>

    @POST("1/deposit")
    suspend fun deposit(
        @Body user: Balance
    ): Response<UserData>

    @GET("1/balance")
    suspend fun balance(): Response<Balance>

    @POST("1/withdraw")
    suspend fun withdraw(
        @Body user: Balance
    ): Response<UserData>
}