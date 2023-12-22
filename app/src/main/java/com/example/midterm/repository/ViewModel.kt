package com.example.midterm.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midterm.models.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class ViewModel(private val repository : Repository) : ViewModel(){

    val myAuthResponse: MutableLiveData<Response<Balance>> = MutableLiveData()
    val deposit : MutableLiveData<Response<UserData>> = MutableLiveData()
    val balance: MutableLiveData<Response<Balance>> = MutableLiveData()
    val withdraw: MutableLiveData<Response<UserData>> = MutableLiveData()

    fun authUser(user: User){
        viewModelScope.launch {
            val response = repository.authUser(user)
            myAuthResponse.value = response
        }
    }

    fun deposit(data: Balance){
        viewModelScope.launch {
            val response = repository.deposit(data)
            deposit.value = response
        }
    }

    fun balance(){
        viewModelScope.launch {
            val response = repository.balance()
            balance.value = response
        }
    }

    fun withdraw(data:Balance){
        viewModelScope.launch {
            val response = repository.withdraw(data)
            withdraw.value = response
        }
    }
}