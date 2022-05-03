package com.example.finalproject.ui.me

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.User
import com.example.finalproject.databinding.ActivityMyInfoBinding

class MyInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val useridInput = binding.useridInput
        val usernameInput = binding.usernameInput
        val sexInput = binding.sexInput
        val birthdayInput = binding.birthdayInput
        val currentUsername = User.getCurrentUsername()
        Toast.makeText(this, "当前用户为$currentUsername", Toast.LENGTH_SHORT).show()

        usernameInput.text = currentUsername
    }
}