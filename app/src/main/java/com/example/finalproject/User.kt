package com.example.finalproject

import com.example.finalproject.db.UserDatabaseHelper

object User {
    private lateinit var currentUsername: String
    fun setCurrentUsername(username: String) {
        currentUsername = username
    }

    fun getCurrentUsername(): String {
        return currentUsername
    }
    
}
