package com.example.finalproject

import androidx.core.content.ContentProviderCompat.requireContext
import com.example.finalproject.db.UserDatabaseHelper

object User {
    private lateinit var currentUsername: String
    fun setCurrentUsername(username: String) {
        currentUsername = username
    }

    fun getCurrentUsername(): String {
        return currentUsername
    }

//    fun findIdByUsername(username: String) {
//        val dbHelper = UserDatabaseHelper(this, "user.db", 1)
//        val db = dbHelper.writableDatabase
//    }
}
