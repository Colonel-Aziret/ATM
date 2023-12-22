package com.example.midterm.models

import java.io.Serializable

data class UserData(
    val id : Int,
    val username: String,
    val password: String,
    val balance: Int
) : Serializable