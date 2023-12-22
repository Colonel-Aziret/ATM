package com.example.midterm.repository

import com.example.midterm.api.RetrofitInstance
import com.example.midterm.models.*
import okhttp3.ResponseBody
import retrofit2.Response

class Repository {

    suspend fun authUser(authUser: User): Response<Balance> {
        return RetrofitInstance.api.authUser(authUser)
    }

    suspend fun deposit(data: Balance): Response<UserData>{
        return RetrofitInstance.api.deposit(data)
    }

    suspend fun balance():Response<Balance>{
        return RetrofitInstance.api.balance()
    }

    suspend fun withdraw(data: Balance):Response<UserData>{
        return RetrofitInstance.api.withdraw(data)
    }
}