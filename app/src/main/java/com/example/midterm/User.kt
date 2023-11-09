package com.example.midterm

import java.io.Serializable

data class User(
    val username: String,
    val password: String,
    var balance: Int
) : Serializable