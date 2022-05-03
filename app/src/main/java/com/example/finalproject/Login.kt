package com.example.finalproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.db.UserDatabaseHelper

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val loginBtn = binding.loginLogin
        val registerBtn = binding.registerLogin
        val usernameView = binding.usernameLogin
        val passwordView = binding.passwordLogin

        Toast.makeText(this, "程序启动", Toast.LENGTH_SHORT).show()

        //登陆响应事件
        loginBtn.setOnClickListener {
            val username = usernameView.text.toString()
            val password = passwordView.text.toString()
            Toast.makeText(this, username, Toast.LENGTH_SHORT).show()
            if (username.isEmpty() || password.isEmpty()
            ) {
                Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show()
            } else if (
                !isExistUser(username)
            ) {
                Toast.makeText(this, "用户名不存在,请先注册", Toast.LENGTH_SHORT).show()
            } else if (password == findPwdByUser(username)) {
                val intent = (Intent(this, HomePage::class.java))
                User.setCurrentUsername(username)
                startActivity(intent)
            } else {
                Toast.makeText(this, "密码错误,请重试", Toast.LENGTH_SHORT).show()
            }
        }

        //注册响应事件
        registerBtn.setOnClickListener {
            val username = usernameView.text.toString()
            val password = passwordView.text.toString()
            Toast.makeText(this, "点击了注册", Toast.LENGTH_SHORT).show()
            if (username.isEmpty() || password.isEmpty()
            ) {
                Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show()
            } else if (
                isExistUser(username)
            ) {
                Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show()
            } else {
                val dbHelper = UserDatabaseHelper(this, "user.db", 1)
                val db = dbHelper.writableDatabase
                val user = ContentValues().apply {
                    put("username", username)
                    put("password", password)
                }
                db.insert("User", null, user)
            }
        }
    }

    @SuppressLint("Range")
    fun isExistUser(username: String): Boolean {

        val dbHelper = UserDatabaseHelper(this, "user.db", 1)
        val db = dbHelper.writableDatabase
        val cursor = db.query(
            "User", arrayOf("username"), "username=?",
            arrayOf(username), null, null, null
        )
        if (cursor.moveToFirst() == false) {
            return false
        } else {
//            cursor.moveToFirst()
            do {
                val dbUsername = cursor.getString(cursor.getColumnIndex("username"))
                if (dbUsername == username) {
                    return true
                }
            } while (cursor.moveToNext())
            cursor.close()
            return false
        }
    }

    @SuppressLint("Range")
    fun findPwdByUser(username: String): String? {
        val dbHelper = UserDatabaseHelper(this, "user.db", 1)
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "User", null, "username=?",
            arrayOf(username), null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val dbUsername = cursor.getString(cursor.getColumnIndex("username"))
                if (dbUsername == username) {
                    return cursor.getString(cursor.getColumnIndex("password"))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return null
    }
}